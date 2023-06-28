package tqs.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tqs.backend.model.User;
import tqs.backend.repository.UserRepository;
import tqs.backend.service.AuthService;

public class AuthService_Tests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@InjectMocks
	private AuthService authService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testRegisterUser_Successful() {
		// Arrange
		String username = "username";
		String email = "email@example.com";
		String password = "password";
		String hashedPassword = "hashedPassword";

		when(userRepository.findByUsername(username)).thenReturn(null);
		when(userRepository.findByEmail(email)).thenReturn(null);
		when(passwordEncoder.encode(password)).thenReturn(hashedPassword);
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
			User user = invocation.getArgument(0);
			user.setId("1"); // Set a sample ID for the saved user
			return user;
		});

		// Act
		User result = authService.registerUser(username, email, password);

		// Assert
		assertNotNull(result);
		assertEquals(username, result.getUsername());
		assertEquals(email, result.getEmail());
		assertEquals(hashedPassword, result.getPassword());
		assertEquals("1", result.getId());

		// Verify that the userRepository methods were called with the correct arguments
		verify(userRepository, times(1)).findByUsername(username);
		verify(userRepository, times(1)).findByEmail(email);
		verify(passwordEncoder, times(1)).encode(password);
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void testRegisterUser_UsernameAlreadyInUse() {
		// Arrange
		String username = "username";
		String email = "email@example.com";
		String password = "password";
		User existingUser = new User();

		when(userRepository.findByUsername(username)).thenReturn(existingUser);

		// Act & Assert
		assertThrows(RuntimeException.class, () -> authService.registerUser(username, email, password));

		// Verify that the userRepository.findByUsername() method was called with the
		// correct argument
		verify(userRepository, times(1)).findByUsername(username);
		verify(userRepository, never()).findByEmail(email);
		verify(passwordEncoder, never()).encode(password);
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testRegisterUser_EmailAlreadyInUse() {

		String username = "username";
		String email = "email@example.com";
		String password = "password";
		User existingUser = new User();

		when(userRepository.findByUsername(username)).thenReturn(null);
		when(userRepository.findByEmail(email)).thenReturn(existingUser);

		assertThrows(RuntimeException.class, () -> authService.registerUser(username, email, password));

		verify(userRepository, times(1)).findByUsername(username);
		verify(userRepository, times(1)).findByEmail(email);
		verify(passwordEncoder, never()).encode(password);
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testLoginUser_Successful() {
		String username = "username";
		String password = "password";
		String hashedPassword = "hashedPassword";
		User user = new User(username, "email@example.com", hashedPassword);

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);

		User result = authService.loginUser(username, password);

		assertNotNull(result);
		assertEquals(username, result.getUsername());
		assertEquals(hashedPassword, result.getPassword());

		verify(userRepository, times(1)).findByUsername(username);
		verify(passwordEncoder, times(1)).matches(password, hashedPassword);
	}

	@Test
	void testLoginUser_InvalidUsernameOrPassword() {
		String username = "username";
		String password = "password";

		when(userRepository.findByUsername(username)).thenReturn(null);

		assertThrows(AuthenticationException.class, () -> authService.loginUser(username, password));

		verify(userRepository, times(1)).findByUsername(username);
		verify(passwordEncoder, never()).matches(anyString(), anyString());
	}

	@Test
	void testLoginUser_InvalidPassword() {
		String username = "username";
		String password = "password";
		String hashedPassword = "hashedPassword";
		User user = new User(username, "email@example.com", hashedPassword);

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(passwordEncoder.matches(password, hashedPassword)).thenReturn(false);

		assertThrows(AuthenticationException.class, () -> authService.loginUser(username, password));

		verify(userRepository, times(1)).findByUsername(username);
		verify(passwordEncoder, times(1)).matches(password, hashedPassword);
	}
}
