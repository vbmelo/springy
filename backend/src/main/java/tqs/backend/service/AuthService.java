package tqs.backend.service;

import javax.naming.AuthenticationException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tqs.backend.model.User;
import tqs.backend.repository.UserRepository;

@Service
public class AuthService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	public User registerUser(String name, String email, String password) {
		if (userRepository.findByEmail(email) != null) {
			throw new RuntimeException("Email already in use");
		}
		String hashedPassword = passwordEncoder.encode(password);

		User user = new User(name, email, hashedPassword);

		return userRepository.save(user);
	}

	public User loginUser(String email, String password) throws AuthenticationException {
		User user = userRepository.findByEmail(email);

		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			return user;
		} else {
			throw new AuthenticationException("Invalid email or password");
		}
	}
}
