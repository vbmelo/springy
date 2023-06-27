package tqs.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mock.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.backend.model.Product;
import tqs.backend.repository.ProductRepository;
import tqs.backend.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductService_Tests {

	@Mock(strictness = Strictness.LENIENT)
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@Test
	void testGetProductByName() {
		// Arrange
		Product product = new Product("product", "description", 10.0,
				"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg");
		when(productRepository.findByName("product")).thenReturn(product);

		// Act
		Product result = productService.getProductByName("product");

		// Assert
		assertEquals(product, result);
	}

	@Test
	void testGetProductById() {
		// Arrange
		Product product = new Product("product", "description", 10.0,
				"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg");
		when(productRepository.findById("1")).thenReturn(Optional.of(product));

		// Act
		Product result = productService.getProductById("1");

		// Assert
		assertEquals(product, result);
	}

	@Test
    void testGetProductById_NonExistentId() {
        // Arrange
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        Product result = productService.getProductById("1");

        // Assert
        assertNull(result);
    }

	@Test
	void testGetAll() {
		// Arrange
		List<Product> productList = new ArrayList<>();
		productList.add(new Product("product1", "description1", 10.0, "image_url1"));
		productList.add(new Product("product2", "description2", 20.0, "image_url2"));
		when(productRepository.findAll()).thenReturn(productList);

		// Act
		List<Product> result = productService.getAll();

		// Assert
		assertEquals(2, result.size());
		assertEquals(productList, result);
	}

	@Test
	void testAddProduct() {
		// Arrange
		Product product = new Product("product", "description", 10.0, "image_url");
		when(productRepository.existsByName("product")).thenReturn(false);
		when(productRepository.save(product)).thenReturn(product);

		// Act
		Product result = productService.addProduct(product);

		// Assert
		assertEquals(product, result);
		verify(productRepository, times(1)).save(product);
	}

	@Test
	void testAddProduct_DuplicateName() {
		// Arrange
		Product product = new Product("product", "description", 10.0, "image_url");
		when(productRepository.existsByName("product")).thenReturn(true);

		// Act
		Product result = productService.addProduct(product);

		// Assert
		assertNull(result);
		verify(productRepository, never()).save(product);
	}

	@Test
	void testDeleteProduct() {
		// Arrange
		String id = "1";

		// Act
		productService.deleteProduct(id);

		// Assert
		verify(productRepository, times(1)).deleteById(id);
	}

}
