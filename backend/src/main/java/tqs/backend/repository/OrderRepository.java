package tqs.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import tqs.backend.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
	Optional<Order> findById(String id);

	List<Order> findByCostumer(String costumer);
}
