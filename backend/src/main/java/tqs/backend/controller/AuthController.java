package tqs.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import tqs.backend.util.JwtTokenUtil;
import tqs.backend.service.AuthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest)
			throws javax.naming.AuthenticationException {
		try {
			final String email = loginRequest.get("email");
			System.out.printf("EMAIL->", email);
			final String password = loginRequest.get("password");
			final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			final String token = jwtTokenUtil.generateToken(userDetails);

			authService.loginUser(email, password);

			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("email", email);
			responseBody.put("token", token);

			return ResponseEntity.ok(responseBody);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Map<String, String> registerRequest) {
		String email = registerRequest.get("email");
		String name = registerRequest.get("name");
		String password = registerRequest.get("password");

		try {
			authService.registerUser(name, email, password);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
		}

		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("message", "Registration successful");
		responseBody.put("name", name);
		responseBody.put("email", email);

		return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
	}
}