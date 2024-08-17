package com.docuart.library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;


@Service
public class JwtUtil {
    private String SECRET_KEY="BUBIRDOCUARTSIFRELEMEANAHTARIDIR";

    private static final String AUTHORITIES_KEY = "auth";

    public String generateToken(String username, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, authorities);
    }

    private String createToken(Map<String, Object> claims, String subject,String username, List<String> authorities) {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject) // ilgili kullanıcı
                .claim("username", username)
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date(System.currentTimeMillis())) // başlangıç
                .setExpiration(new Date(System.currentTimeMillis() +  8760 * 60 * 60 * 1000)) // bitiş 12 saat
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // kullanılan algoritma ve bu algoritma çalışırken kullanılacak hash key değeri
                .compact();
    }


    //verilen tokena ait kullanıcı adını döndürürü
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public ArrayList<String> extractAuthorities(String token) {
        return (ArrayList<String>)extractAllClaims(token).get("auth");
    }

    // verilen token a ait token bitiş süresini verir.
    public Date extractExpiration(String token) {
        System.out.println(extractClaim(token, Claims::getExpiration));
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // verilen token a ait claims bilgisini alır.
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // token ın geçerlilik süre doldu mu?
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject, List<String> authorities) {

        return Jwts.builder().setClaims(claims)
                .setSubject(subject) // ilgili kullanıcı
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date(System.currentTimeMillis())) // başlangıç
                .setExpiration(new Date(System.currentTimeMillis() + 72 * 60 * 60 * 1000)) // bitiş 24 saat
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // kullanılan algoritma ve bu algoritma çalışırken kullanılacak hash key değeri
                .compact();
    }

    // token hala geçerli mi? kullanıcı adı doğru ise ve token ın geçerlilik süresi devam ediyorsa true döner.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
