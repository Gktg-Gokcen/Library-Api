package com.docuart.library.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class JwtUtil {

    private String SECRET_KEY="BUBIRDOCUARTSIFRELEMEANAHTARIDIR";
    private static final String AUTHORITIES_KEY = "auth";

    public String generateToken(String kullaniciAdi, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, "test", kullaniciAdi, authorities);
    }

    private String createToken(Map<String, Object> claims, String subject,String kullaniciAdi, List<String> authorities) {

        return Jwts.builder().setClaims(claims)
                .setSubject(subject) // ilgili kullanıcı
                .claim("kullaniciAdi", kullaniciAdi)
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date(System.currentTimeMillis())) // başlangıç
                .setExpiration(new Date(System.currentTimeMillis() +  8760 * 60 * 60 * 1000)) // bitiş 12 saat
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // kullanılan algoritma ve bu algoritma çalışırken kullanılacak hash key değeri
                .compact();
    }

}
