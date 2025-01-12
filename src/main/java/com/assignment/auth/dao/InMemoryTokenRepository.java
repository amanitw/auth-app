package com.assignment.auth.dao;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryTokenRepository implements TokenRepository {
    private Map<String, Long> tokenStore = new HashMap<>();
    private Map<String, Boolean> revokedTokens = new HashMap<>();

    @Override
    public void storeToken(String token, long expiryTime) {
        tokenStore.put(token, expiryTime);
    }

    public boolean isTokenValid(String token) {
        Long expiryTime = tokenStore.get(token);
        if (expiryTime == null || revokedTokens.getOrDefault(token, false)) {
            return false;
        }
        return expiryTime > System.currentTimeMillis();
    }

    @Override
    public void revokeToken(String token) {
        revokedTokens.put(token, true);
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokens.getOrDefault(token, false);
    }

    public void cleanExpiredTokens() {
        long currentTime = System.currentTimeMillis();
        tokenStore.entrySet().removeIf(entry -> entry.getValue() < currentTime);
    }
}
