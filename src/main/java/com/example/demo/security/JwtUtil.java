package com.example.demo.security;

import com.example.demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationTimeMs;

    // No-arg constructor required by the test class
    public JwtUtil() {
        this.secretKey = Keys.hmacShaKeyFor(
                "test-secret-for-unit-tests-only-this-is-very-important-1234567890abcdef"
                        .getBytes(StandardCharsets.UTF_8));
        this.expirationTimeMs = 864_000_000L; // ~10 days
    }

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration:864000000}") long expirationTimeMs) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTimeMs = expirationTimeMs;
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateTokenForUser(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        return generateToken(claims, user.getEmail());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername != null && extractedUsername.equals(username)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Updated for jjwt 0.12.x - uses the new parser API
     * This method signature remains Jws<Claims> so your existing tests should work
     */
    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)              // ← new method in 0.12+
                .build()
                .parseSignedClaims(token);          // ← new method name (replaces parseClaimsJws)
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Updated to use getPayload() instead of getBody()
        final Claims claims = parseToken(token).getPayload();
        return claimsResolver.apply(claims);
    }
}