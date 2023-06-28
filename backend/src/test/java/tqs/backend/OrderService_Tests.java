package tqs.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.backend.model.Order;
import tqs.backend.repository.OrderRepository;
import tqs.backend.service.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderService_Tests {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderService orderService;

	@Test
	void testFindByCustomer() {
		String customer = "John Doe";
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(new Date(), new Date(), new ArrayList<>(), "pending", customer, "Pickup Point 1"));
		orders.add(new Order(new Date(), new Date(), new ArrayList<>(), "delivered", customer, "Pickup Point 2"));

		when(orderRepository.findByCostumer(customer)).thenReturn(orders);

		List<Order> result = orderService.findByCostumer(customer);

		assertEquals(2, result.size());
		assertEquals(customer, result.get(0).getCostumer());
		assertEquals(customer, result.get(1).getCostumer());

		verify(orderRepository, times(1)).findByCostumer(customer);
		verifyNoMoreInteractions(orderRepository);
	}

	@Test
	void testGetAll() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(new Date(), new Date(), new ArrayList<>(), "pending", "Customer 1", "Pickup Point 1"));
		orders.add(new Order(new Date(), new Date(), new ArrayList<>(), "delivered", "Customer 2", "Pickup Point 2"));

		when(orderRepository.findAll()).thenReturn(orders);

		List<Order> result = orderService.getAll();

		assertEquals(2, result.size());

		verify(orderRepository, times(1)).findAll();
		verifyNoMoreInteractions(orderRepository);
	}

	@Test
	void testCreateOrder() {
		Order order = new Order(new Date(), new ArrayList<>(), "Customer 1", "Pickup Point 1");
		when(orderRepository.save(order)).thenReturn(order);

		Order result = orderService.createOrder(order);

		assertNotNull(result);
		assertNotNull(result.getDateCreated());
		assertEquals("pending", result.getStatus());

		verify(orderRepository, times(1)).save(order);
		verifyNoMoreInteractions(orderRepository);
	}

	@Test
	void testDeleteOrder() {
		String orderId = "12345";

		orderService.deleteOrder(orderId);

		verify(orderRepository, times(1)).deleteById(orderId);
		verifyNoMoreInteractions(orderRepository);
	}
}
