package com.assignment.auth.service;

import com.assignment.auth.exception.InvalidEmailException;
import com.assignment.auth.exception.PassWordException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CredentialsValidator {
    public void validateInput(String email, String password) throws InvalidEmailException, PassWordException {
        if (email==null){
            throw new InvalidEmailException("Email is required");
        }
        if (password==null){
            throw new PassWordException("Password is required");
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new InvalidEmailException("Invalid email format: " + email);
        }
        if (password.isEmpty()){
            throw new PassWordException("Password cannot be empty");
        }
    }
}
