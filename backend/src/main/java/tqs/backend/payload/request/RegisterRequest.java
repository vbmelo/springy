package tqs.backend.payload.request;

import org.springframework.data.mongodb.core.mapping.Field;

public class RegisterRequest {
	@Field("username")
	private String username;
	@Field("email")
	private String email;
	@Field("password")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
