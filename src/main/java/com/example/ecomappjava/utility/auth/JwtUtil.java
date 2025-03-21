package com.example.ecomappjava.utility.auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Map;

@Getter
public class JwtUtil {
    private final MacAlgorithm algorithm = Jwts.SIG.HS256;
    private final SecretKey secretKey = algorithm.key().build();

    public String generateToken(Map<String, Object> userClaims) {
        return Jwts.builder()
                .claims(userClaims)
                .signWith(secretKey)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            Jws<Claims> jwsClaims = Jwts.parser()
                    .verifyWith(secretKey)  // Use the same key that was used to sign
                    .build()
                    .parseSignedClaims(token);

            return jwsClaims.getPayload(); // Return claims if valid
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired JWT token", e);
        }
    }

    public Claims getClaims(String token) {
        Claims claims = validateToken(token);
        return claims;
    }

    public boolean isAdmin(String token) {
        Claims claims = validateToken(token);
        List<String> roles = claims.get("roles", List.class);
        return roles.contains("ADMIN");
    }

}
