package tqs.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tqs.backend.model.Order;
import tqs.backend.repository.OrderRepository;

public class OrderService {
	@Autowired
	private OrderRepository orderRepository;

	public Order findByOrderId(String orderId) {
		return orderRepository.findByOrderId(orderId);
	};

	public Boolean existsByOrderId(String orderId) {
		return orderRepository.existsByOrderId(orderId);
	};

	public List<Order> findByUserId(String userId) {
		return orderRepository.findByUserId(userId);
	};
}
