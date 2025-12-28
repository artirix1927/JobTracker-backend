package com.artyom.jobtracker.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.artyom.jobtracker.entity.UserStatus;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
    private final Key key;
    private final long accessExpiration = 1000 * 60 * 60; // 1 hour
    private final long refreshExpiration = 1000 * 60 * 60 * 24 * 7; // 7 days


    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String email, UserStatus role) {
        return Jwts.builder()
                .setSubject(email) // unique identity
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String email, UserStatus role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(key)
                .compact();
    }

    
    public String getEmail(String token) {
        return getClaims(token).getSubject();   // ✅ FIXED
    }

    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}