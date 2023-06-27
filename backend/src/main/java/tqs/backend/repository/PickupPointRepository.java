package tqs.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import tqs.backend.model.PickupPoint;

public interface PickupPointRepository extends MongoRepository<PickupPoint, String> {
	PickupPoint findByName(String name);

	Boolean existsByName(String name);
}
