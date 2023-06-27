package tqs.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import tqs.backend.payload.request.LoginRequest;
import tqs.backend.payload.request.RegisterRequest;
// import tqs.backend.payload.response.UserInfoResponse;
import tqs.backend.service.AuthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@Autowired
	public AuthController(
			@Qualifier("customUserDetailsService") UserDetailsService userDetailsService, AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws javax.naming.AuthenticationException {
		try {
			String username = loginRequest.getUsername();
			String password = loginRequest.getPassword();

			authService.loginUser(username, password);

			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("status", HttpStatus.OK.value());
			responseBody.put("username", username);

			return ResponseEntity.ok().body(responseBody);
		} catch (AuthenticationException e) {
			Map<String, Object> errorBody = new HashMap<>();
			errorBody.put("status", HttpStatus.UNAUTHORIZED.value());
			errorBody.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
		String email = registerRequest.getEmail();
		String username = registerRequest.getUsername();
		String password = registerRequest.getPassword();

		try {
			authService.registerUser(username, email, password);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}

		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", "Registration successful");
		responseBody.put("username", username);
		responseBody.put("email", email);

		return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
	}
}
