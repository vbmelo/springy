package tqs.backend.payload.request;

import org.springframework.data.mongodb.core.mapping.Field;

public class LoginRequest {
	@Field("username")
	private String username;
	@Field("password")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
