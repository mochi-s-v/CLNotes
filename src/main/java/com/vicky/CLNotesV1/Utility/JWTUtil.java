package com.vicky.CLNotesV1.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    private final String SECRET = "my-super-secret-key-that-is-long-enough-1234567890!@#$";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username) {
        long EXPIRATION_TIME = 60 * 60 * 1000;
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String username, UserDetails userDetails, String token) {
        return userDetails.getUsername().equals(username) && !isExpired(token);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isExpired(String token) {
        Claims claim = getClaims(token);
        return claim.getExpiration().before(new Date());
    }
}
