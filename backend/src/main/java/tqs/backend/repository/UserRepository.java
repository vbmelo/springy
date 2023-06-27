package tqs.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tqs.backend.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	User findByEmail(String email);

	User findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
