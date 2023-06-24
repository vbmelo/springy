package tqs.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import tqs.backend.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	User findByEmail(String email);
}
