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
		Product product = new Product("product", "description", 10.0,
				"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg");
		when(productRepository.findByName("product")).thenReturn(product);

		Product result = productService.getProductByName("product");

		assertEquals(product, result);
	}

	@Test
	void testGetProductById() {
		Product product = new Product("product", "description", 10.0,
				"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg");
		when(productRepository.findById("1")).thenReturn(Optional.of(product));

		Product result = productService.getProductById("1");

		assertEquals(product, result);
	}

	@Test
    void testGetProductById_NonExistentId() {
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        Product result = productService.getProductById("1");

        assertNull(result);
    }

	@Test
	void testGetAll() {
		List<Product> productList = new ArrayList<>();
		productList.add(new Product("product1", "description1", 10.0, "image_url1"));
		productList.add(new Product("product2", "description2", 20.0, "image_url2"));
		when(productRepository.findAll()).thenReturn(productList);

		List<Product> result = productService.getAll();

		assertEquals(2, result.size());
		assertEquals(productList, result);
	}

	@Test
	void testAddProduct() {
		Product product = new Product("product", "description", 10.0, "image_url");
		when(productRepository.existsByName("product")).thenReturn(false);
		when(productRepository.save(product)).thenReturn(product);

		Product result = productService.addProduct(product);

		assertEquals(product, result);
		verify(productRepository, times(1)).save(product);
	}

	@Test
	void testAddProduct_DuplicateName() {
		Product product = new Product("product", "description", 10.0, "image_url");
		when(productRepository.existsByName("product")).thenReturn(true);

		Product result = productService.addProduct(product);

		assertNull(result);
		verify(productRepository, never()).save(product);
	}

	@Test
	void testDeleteProduct() {
		String id = "1";

		productService.deleteProduct(id);

		verify(productRepository, times(1)).deleteById(id);
	}
}
