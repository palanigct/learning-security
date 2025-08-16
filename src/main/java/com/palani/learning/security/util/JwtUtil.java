package com.palani.learning.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for generating and validating JWT tokens.
 * <p>
 * Handles token creation, extraction, and validation for authentication.
 * </p>
 */
@Component
public class JwtUtil {

    // Base64-encoded 256-bit key (you can generate one securely)
    private static final String SECRET_KEY = "qwertyuiopasdfghjklzxcvbnm1234567890abcdefghijklmnopqrstuvwxyz"; // Must be 64+ chars for HS256

    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token for the given username.
     *
     * @param username the username to include in the token
     * @return the generated JWT token
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Validates a JWT token against the provided username.
     *
     * @param token the JWT token
     * @param username the username to validate against
     * @return true if the token is valid and not expired, false otherwise
     */
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username)) && !isTokenExpired(token);
    }

    /**
     * Checks if a JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    /**
     * Extracts claims from a JWT token.
     *
     * @param token the JWT token
     * @return the claims contained in the token
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
