package com.taskhub.taskmanager.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import com.taskhub.taskmanager.entity.User;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET = "YOUR_SECRET_KEY_SHOULD_BE_AT_LEAST_256_BITS_LONG";

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return resolver.apply(claims);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, User user) {
        return extractUsername(token).equals(user) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
