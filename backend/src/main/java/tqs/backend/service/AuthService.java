package tqs.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tqs.backend.model.User;
import tqs.backend.repository.UserRepository;
import org.springframework.security.core.AuthenticationException;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User registerUser(String username, String email, String password) {
		if (userRepository.findByUsername(username) != null) {
			throw new RuntimeException("Username already in use");
		}
		if (userRepository.findByEmail(email) != null) {
			throw new RuntimeException("Email already in use");
		}
		String hashedPassword = passwordEncoder.encode(password);

		User user = new User(username, email, hashedPassword);

		return userRepository.save(user);
	}

	public User loginUser(String username, String password) throws AuthenticationException {
		User user = userRepository.findByUsername(username);

		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			return user;
		} else {
			throw new AuthenticationException("Invalid username or password") {
			};
		}
	}

}
