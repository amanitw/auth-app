package com.assignment.auth.service;

import com.assignment.auth.dao.UserRepository;
import com.assignment.auth.exception.InvalidCredentialsException;
import com.assignment.auth.exception.InvalidEmailException;
import com.assignment.auth.exception.PassWordException;
import com.assignment.auth.exception.UserAlreadyExistException;
import com.assignment.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CredentialsValidator credentialsValidator;

    @Override
    public void registerUser(String email, String password) throws UserAlreadyExistException, InvalidEmailException, PassWordException {
        if (userRepository.getUserByEmail(email).isPresent()) {
            throw new UserAlreadyExistException(email + " user already exist\n");
        }
        credentialsValidator.validateInput(email, password);
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(email, encodedPassword);
        userRepository.save(user);
    }

    @Override
    public void authenticate(String email, String password) throws InvalidCredentialsException {
        Optional<User> optionalUser = userRepository.getUserByEmail(email);
        if (!(optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword()))) {
            throw new InvalidCredentialsException("Invalid credentials\n");
        }
    }

}
