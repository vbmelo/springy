package tqs.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mock.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.backend.model.User;
import tqs.backend.repository.UserRepository;
import tqs.backend.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserService_Tests {

	@Mock(strictness = Strictness.LENIENT)
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
    void testExistsByEmail() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean result = userService.existsByEmail("test@example.com");

        assertTrue(result);
    }

	@Test
    void testExistsByUsername() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        boolean result = userService.existsByUsername("testuser");

        assertTrue(result);
    }

	@Test
	void testFindByEmail() {
		User user = new User("testuser", "test@example.com", "password");
		when(userRepository.findByEmail("test@example.com")).thenReturn(user);

		User result = userService.findByEmail("test@example.com");

		assertEquals(user, result);
	}

	@Test
	void testFindByUsername() {
		User user = new User("testuser", "test@example.com", "password");
		when(userRepository.findByUsername("testuser")).thenReturn(user);

		User result = userService.findByUsername("testuser");

		assertEquals(user, result);
	}

	@Test
	void testSave() {
		User user = new User("testuser", "test@example.com", "password");
		when(userRepository.save(user)).thenReturn(user);

		User result = userService.save(user);

		assertEquals(user, result);
		verify(userRepository, times(1)).save(user);
	}
}
