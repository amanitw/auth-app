package com.assignment.auth.controller;

import com.assignment.auth.exception.UserAlreadyExistException;
import com.assignment.auth.model.User;
import com.assignment.auth.service.AuthService;
import com.assignment.auth.service.JwtService;
import com.assignment.auth.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user){
        try {
            authService.registerUser(user.getEmail(), user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body("User has been registered!!\n");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody User user){
        try {
            authService.authenticate(user.getEmail(), user.getPassword());
            String token = jwtService.generateToken(user.getEmail());
            jwtService.storeToken(token);
            return ResponseEntity.status(HttpStatus.OK).body("Token: "+token+"\n");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/protected")
    public ResponseEntity<String> protectedEndpoint() {
        return ResponseEntity.ok("You have accessed a protected resource");
    }

    @PostMapping("/revoke")
    public ResponseEntity<String> revokeToken(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        jwtService.revokeToken(token);
        return ResponseEntity.ok("Token revoked!!");
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        try {
            String newToken = jwtService.renewToken(token);
            jwtService.storeToken(newToken);
            return ResponseEntity.ok("New token: "+newToken);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token Not renewed: "+e.getMessage());
        }
    }
}
