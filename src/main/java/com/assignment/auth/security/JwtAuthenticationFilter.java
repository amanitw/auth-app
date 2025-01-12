package com.assignment.auth.security;

import com.assignment.auth.dao.TokenRepository;
import com.assignment.auth.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token is missing");
            return;
        }

        String token = authHeader.substring(7);

        try {
            if (!JwtUtil.validateToken(token, tokenRepository)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or Expired Toke");
                return;
            }
        } catch (ExpiredJwtException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired: "+e.getMessage());
            return;
        } catch (JwtException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token "+e.getMessage());
            return;
        }

        filterChain.doFilter(request,response);
    }
}
