package tqs.backend.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.backend.model.Order;
import tqs.backend.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	public List<Order> findByCostumer(String customer) {
		return orderRepository.findByCostumer(customer);
	}

	public List<Order> getAll() {
		return orderRepository.findAll();
	}

	public Order createOrder(Order order) {
		// Define a data atual como a data de criação
		order.setDateCreated(new Date());
		// Define o status como "pending"
		order.setStatus("pending");

		return orderRepository.save(order);
	}

	public void deleteOrder(String id) {
		orderRepository.deleteById(id);
	}
}
