package tqs.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
	private static final String SECRET_KEY = "AS4356DNI!*(&@H!@ND514!O*AS&GD()!@BA123LKSJ7377D)!(Dalsio5754[]yjth33(*!&#G$(&@*^BDF!@)(35dhLADHA7(&!!bnikldh!@))";

	private final BCryptPasswordEncoder passwordEncoder;

	public JwtTokenUtil(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	private Key getSigningKey() {
		byte[] secretBytes = SECRET_KEY.getBytes();
		return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + 86400000); // Token validity: 24 hours

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(expirationDate)
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public boolean isTokenExpired(String token) {
		Date expirationDate = Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getExpiration();
		return expirationDate.before(new Date());
	}

	public String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	public boolean matchesPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
