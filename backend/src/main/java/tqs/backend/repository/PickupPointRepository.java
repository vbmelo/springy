package tqs.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import tqs.backend.model.PickupPoint;

@Repository
public interface PickupPointRepository extends MongoRepository<PickupPoint, String> {
	PickupPoint findByName(String name);

	Boolean existsByName(String name);
}
