package com.docuart.library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {
    private static final String SECRET_KEY = "BUBIRDOCUARTSIFRELEMEANAHTARIDIR";
    private static final String AUTHORITIES_KEY = "auth";

    public String generateToken(String username, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, authorities);
    }

    private String createToken(Map<String, Object> claims, String subject, List<String> authorities) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 72 * 60 * 60 * 1000)) // 72 saat
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public ArrayList<String> extractAuthorities(String token) {
        return (ArrayList<String>) extractAllClaims(token).get(AUTHORITIES_KEY);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
