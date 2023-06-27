package tqs.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import tqs.backend.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	Product findByName(String name);

	Boolean existsByName(String name);
}
