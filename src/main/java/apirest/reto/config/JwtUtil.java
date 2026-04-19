package apirest.reto.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	// el token dura 24 horas
	private static final long EXPIRACION = 86400000;

	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generarToken(String username, String rol) {
		return Jwts.builder()
				.subject(username)
				.claim("rol", rol)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRACION))
				.signWith(getKey())
				.compact();
	}

	public String obtenerUsername(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	public String obtenerRol(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("rol", String.class);
	}

	public boolean esTokenValido(String token) {
		try {
			Jwts.parser()
					.verifyWith(getKey())
					.build()
					.parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
