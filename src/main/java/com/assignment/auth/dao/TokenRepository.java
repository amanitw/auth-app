package com.assignment.auth.dao;

public interface TokenRepository {
    void storeToken(String token,long expiryTime);
    void revokeToken(String token);
    boolean isTokenValid(String token);
    boolean isTokenRevoked(String token);
    void cleanExpiredTokens();
}
