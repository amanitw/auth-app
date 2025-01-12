package com.assignment.auth.service;

import com.assignment.auth.dao.TokenRepository;
import com.assignment.auth.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Autowired
    private TokenRepository tokenRepository;

    public String generateToken(String email){
        return JwtUtil.generateToken(email);
    }

    public void storeToken(String token){
        tokenRepository.storeToken(token,System.currentTimeMillis()+60000);
    }

    public void revokeToken(String token) {
        tokenRepository.revokeToken(token);
    }

    public String renewToken(String token){
         return JwtUtil.renewToken(token,tokenRepository);
    }
}
