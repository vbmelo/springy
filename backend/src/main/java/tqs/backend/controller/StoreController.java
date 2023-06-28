package tqs.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tqs.backend.model.Order;
import tqs.backend.model.PickupPoint;
import tqs.backend.model.Product;
import tqs.backend.service.ProductService;
import tqs.backend.service.OrderService;
import tqs.backend.service.PickupPointService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/estore")
public class StoreController {

	@Autowired
	private ProductService productService;

	@Autowired
	private PickupPointService pickupPointService;

	@Autowired
	private OrderService orderService;

	@GetMapping("/get_product")
	public ResponseEntity<?> getItem(@RequestParam String productId) {
		Product product = productService.getProductById(productId);

		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Product not found!");
		}
	}

	@GetMapping("/get_products")
	public ResponseEntity<?> getAllProducts() {
		List<Product> products = productService.getAll();

		if (!products.isEmpty()) {
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No products found!");
		}
	}

	@PostMapping(value = "/add_product", consumes = "application/json")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		Product addedProduct = productService.addProduct(product);

		if (addedProduct != null) {
			return ResponseEntity.ok(addedProduct);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Product already exists!");
		}
	}

	@GetMapping("/get_points")
	public ResponseEntity<?> getPoints() {
		List<PickupPoint> points = pickupPointService.getAll();

		if (!points.isEmpty()) {
			return ResponseEntity.ok(points);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No pickup points found!");
		}
	}

	@PostMapping(value = "/create_point", consumes = "application/json")
	public ResponseEntity<?> createPoint(@RequestBody PickupPoint point) {
		PickupPoint point_ = pickupPointService.addPickupPoint(point);

		if (point_ != null) {
			return ResponseEntity.ok(point_);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Pickup Point name already exists!");
		}
	}

	@PostMapping(value = "/create_order", consumes = "application/json")
	public ResponseEntity<?> createOrder(@RequestBody Order order) {
		PickupPoint point = pickupPointService.getPickupPoint(order.getPickupPoint());

		if (point == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Pickup point not found!");
		}

		// Set the current date as the dateCreated
		order.setDateCreated(new Date());
		// Set the status as "pending"
		order.setStatus("pending");

		Order createdOrder = orderService.createOrder(order);

		PickupPoint pointWithNewOrder = pickupPointService.addOrderToPickupPoint(point.getName(), order);

		if (pointWithNewOrder == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Adding order to pickup point error!");
		}

		if (createdOrder != null) {
			return ResponseEntity.ok(createdOrder);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Failed to create a new order!");
		}
	}

	@GetMapping("/get_orders")
	public ResponseEntity<?> getAllOrders() {
		List<Order> orders = orderService.getAll();

		if (!orders.isEmpty()) {
			return ResponseEntity.ok(orders);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: No orders found!");
		}
	}

}
