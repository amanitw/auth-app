package com.assignment.auth.service;

import com.assignment.auth.exception.InvalidCredentialsException;
import com.assignment.auth.exception.InvalidEmailException;
import com.assignment.auth.exception.PassWordException;
import com.assignment.auth.exception.UserAlreadyExistException;
import com.assignment.auth.model.User;

public interface AuthService {
    void registerUser(String email,String password) throws UserAlreadyExistException, InvalidEmailException, PassWordException;
    void authenticate(String email,String password) throws InvalidCredentialsException;
}
