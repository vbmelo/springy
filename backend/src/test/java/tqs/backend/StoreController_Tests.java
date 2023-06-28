package tqs.backend;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mock.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.backend.controller.StoreController;
import tqs.backend.model.Order;
import tqs.backend.model.PickupPoint;
import tqs.backend.model.Product;
import tqs.backend.service.OrderService;
import tqs.backend.service.PickupPointService;
import tqs.backend.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StoreController_Tests {

	@Mock(strictness = Strictness.LENIENT)
	private ProductService productService;

	@Mock(strictness = Strictness.LENIENT)
	private PickupPointService pickupPointService;

	@Mock(strictness = Strictness.LENIENT)
	private OrderService orderService;

	@InjectMocks
	private StoreController storeController;

	@Test
	void testGetItem_ExistingProduct() {
		String productId = "1";
		Product product = new Product("Test Product", "Description", 10.0, "https://example.com/image.jpg");
		when(productService.getProductById(productId)).thenReturn(product);

		ResponseEntity<?> response = storeController.getItem(productId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(product, response.getBody());
	}

	@Test
	void testGetItem_NonExistingProduct() {
		String productId = "1";
		when(productService.getProductById(productId)).thenReturn(null);

		ResponseEntity<?> response = storeController.getItem(productId);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Error: Product not found!", response.getBody());
	}

	@Test
	void testGetAllProducts_NotEmptyList() {
		List<Product> productList = new ArrayList<>();
		productList.add(new Product("Test Product 1", "Description 1", 10.0, "https://example.com/image1.jpg"));
		productList.add(new Product("Test Product 2", "Description 2", 20.0, "https://example.com/image2.jpg"));
		when(productService.getAll()).thenReturn(productList);

		ResponseEntity<?> response = storeController.getAllProducts();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(productList, response.getBody());
	}

	@Test
    void testGetAllProducts_EmptyList() {
        when(productService.getAll()).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = storeController.getAllProducts();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: No products found!", response.getBody());
    }

	@Test
	void testAddProduct_ValidProduct() {
		Product product = new Product("Test Product", "Description", 10.0, "https://example.com/image.jpg");
		when(productService.addProduct(product)).thenReturn(product);

		ResponseEntity<?> response = storeController.addProduct(product);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(product, response.getBody());
	}

	@Test
	void testAddProduct_DuplicateProduct() {
		Product product = new Product("Test Product", "Description", 10.0, "https://example.com/image.jpg");
		when(productService.addProduct(product)).thenReturn(null);

		ResponseEntity<?> response = storeController.addProduct(product);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Error: Product already exists!", response.getBody());
	}

	@Test
	void testGetPoints_NotEmptyList() {
		List<PickupPoint> pointsList = new ArrayList<>();
		pointsList.add(new PickupPoint("Point 1", "Address 1"));
		pointsList.add(new PickupPoint("Point 2", "Address 2"));
		when(pickupPointService.getAll()).thenReturn(pointsList);

		ResponseEntity<?> response = storeController.getPoints();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(pointsList, response.getBody());
	}

	@Test
    void testGetPoints_EmptyList() {
        when(pickupPointService.getAll()).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = storeController.getPoints();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: No pickup points found!", response.getBody());
    }

	@Test
	void testCreatePoint_ValidPoint() {
		PickupPoint point = new PickupPoint("Point 1", "Address 1");
		when(pickupPointService.addPickupPoint(point)).thenReturn(point);

		ResponseEntity<?> response = storeController.createPoint(point);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(point, response.getBody());
	}

	@Test
	void testCreatePoint_DuplicatePoint() {
		PickupPoint point = new PickupPoint("Point 1", "Address 1");
		when(pickupPointService.addPickupPoint(point)).thenReturn(null);

		ResponseEntity<?> response = storeController.createPoint(point);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Error: Pickup Point name already exists!", response.getBody());
	}

	@Test
	void testCreateOrder_ValidOrder() {
		PickupPoint point = new PickupPoint("Point 1", "Address 1");
		Order order = new Order("Test Order", "John Doe", "john@example.com", point.getName());
		when(pickupPointService.getPickupPoint(order.getPickupPoint())).thenReturn(point);
		when(orderService.createOrder(order)).thenReturn(order);
		when(pickupPointService.addOrderToPickupPoint(point.getName(), order)).thenReturn(point);

		ResponseEntity<?> response = storeController.createOrder(order);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(order, response.getBody());
	}

	@Test
	void testCreateOrder_InvalidPickupPoint() {
		Order order = new Order("Test Order", "John Doe", "john@example.com", "Non-existing Point");
		when(pickupPointService.getPickupPoint(order.getPickupPoint())).thenReturn(null);

		ResponseEntity<?> response = storeController.createOrder(order);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Error: Pickup point not found!", response.getBody());
	}

	@Test
	void testCreateOrder_FailedToAddOrderToPickupPoint() {
		PickupPoint point = new PickupPoint("Point 1", "Address 1");
		Order order = new Order("Test Order", "John Doe", "john@example.com", point.getName());
		when(pickupPointService.getPickupPoint(order.getPickupPoint())).thenReturn(point);
		when(orderService.createOrder(order)).thenReturn(order);
		when(pickupPointService.addOrderToPickupPoint(point.getName(), order)).thenReturn(null);

		ResponseEntity<?> response = storeController.createOrder(order);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Error: Adding order to pickup point error!", response.getBody());
	}

	@Test
	void testGetAllOrders_NotEmptyList() {
		List<Order> orderList = new ArrayList<>();
		orderList.add(new Order("Order 1", "John Doe", "john@example.com", "Point 1"));
		orderList.add(new Order("Order 2", "Jane Smith", "jane@example.com", "Point 2"));
		when(orderService.getAll()).thenReturn(orderList);

		ResponseEntity<?> response = storeController.getAllOrders();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(orderList, response.getBody());
	}

	@Test
    void testGetAllOrders_EmptyList() {
        when(orderService.getAll()).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = storeController.getAllOrders();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error: No orders found!", response.getBody());
    }
}
