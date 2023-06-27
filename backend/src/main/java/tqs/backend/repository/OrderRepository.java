package tqs.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import tqs.backend.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	Order findByOrderId(String orderId);

	Boolean existsByOrderId(String orderId);

	List<Order> findByUserId(String userId);
}
