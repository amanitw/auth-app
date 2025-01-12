package com.assignment.auth.utils;

import com.assignment.auth.dao.InMemoryTokenRepository;
import com.assignment.auth.dao.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtUtil {
    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String email){
        long expirationTime = 60000;

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(key)
                .compact();
    }

    public static boolean validateToken(String token,TokenRepository tokenRepository){
        if (!tokenRepository.isTokenValid(token)){
            return false;
        }

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e){
            throw new JwtException("Token expired", e);
        } catch (JwtException e){
            throw new JwtException("Invalid Token", e);
        }
    }

    public static String getEmail(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token)
                .getBody().get("username", String.class);
    }

    public static String renewToken(String oldToken, TokenRepository tokenRepository){
        if (!validateToken(oldToken, tokenRepository)) {
            throw new IllegalArgumentException("Invalid or expired token.");
        }
        String username = getEmail(oldToken);
        return generateToken(username);
    }
}
