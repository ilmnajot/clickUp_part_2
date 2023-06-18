package com.example.clickup_part_2.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    @Autowired
    @Lazy
    JwtFilter jwtFilter;

    private final long expireTime = 7L * 24 * 3600 * 1000;
    private final Long expiration = 30*24*3600*1000L*1000;
    private final String secretWords = "mySecretWordsAreHereActually";

    public String generateToken(String username) {
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime + expiration))
                .signWith(SignatureAlgorithm.HS512, secretWords)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretWords)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }
}
