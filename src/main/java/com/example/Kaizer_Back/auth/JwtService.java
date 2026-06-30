package com.example.Kaizer_Back.auth;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final SecretKey key;
	private final long expirationMinutes;

	public JwtService(
			@Value("${app.jwt.secret}") String secret,
			@Value("${app.jwt.expiration-minutes}") long expirationMinutes
	) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMinutes = expirationMinutes;
	}

	public String generateToken(UserDetails userDetails) {
		Instant now = Instant.now();
		Instant exp = now.plus(expirationMinutes, ChronoUnit.MINUTES);

		var builder = Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(Date.from(now))
				.expiration(Date.from(exp));

		// Incrusta el ID primario, nombre y rol para evitar consultas redundantes a la BD.
		if (userDetails instanceof UsuarioPrincipal principal) {
			builder.claim("uid", principal.getId());
			if (principal.getNombre() != null) builder.claim("nombre", principal.getNombre());
			principal.getAuthorities().stream()
					.findFirst()
					.ifPresent(a -> {
						String authority = a.getAuthority();
						builder.claim("role", authority.startsWith("ROLE_")
								? authority.substring(5)
								: authority);
					});
		}

		return builder.signWith(key).compact();
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public Long extractUserId(String token) {
		Object uid = extractAllClaims(token).get("uid");
		if (uid instanceof Number n) return n.longValue();
		return null;
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
