package tqs.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "users")
public class User {

	@Id
	private String id;

	@Field("username")
	private String username;

	@Field("email")
	private String email;

	@Field("password")
	private String password;

	public User(String id, String username, String email, String password) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "{" +
				" id='" + getId() + "'" +
				", username='" + getUsername() + "'" +
				", email='" + getEmail() + "'" +
				", password='" + getPassword() + "'" +
				"}";
	}

	public GrantedAuthority getAuthorities() {
		return null;
	}

}
