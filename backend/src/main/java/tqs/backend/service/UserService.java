package tqs.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.backend.model.User;
import tqs.backend.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public boolean existsByEmail(String email) {
		return true;
	}

	public boolean existsByName(String name) {
		return true;
	}

	public User save(User user) {
		return userRepository.save(user);
	}
}
