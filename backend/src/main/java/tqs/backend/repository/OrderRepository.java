package tqs.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import tqs.backend.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

}
