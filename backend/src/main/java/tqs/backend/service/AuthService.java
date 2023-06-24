package tqs.backend.service;

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
		// Verificar se o email já está em uso
		if (userRepository.findByEmail(email) != null) {
			throw new RuntimeException("Email already in use");
		}

		// Criptografar a senha antes de salvar
		String hashedPassword = passwordEncoder.encode(password);

		// Criar o objeto User com os dados fornecidos
		User user = new User(name, email, hashedPassword);

		// Salvar o usuário no banco de dados
		return userRepository.save(user);
	}

	public User loginUser(String email, String password) {
		// Buscar o usuário pelo email
		User user = userRepository.findByEmail(email);

		// Verificar se o usuário existe e se a senha está correta
		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			return user;
		} else {
			throw new RuntimeException("Invalid email or password");
		}
	}
}
