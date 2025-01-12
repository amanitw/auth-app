package com.assignment.auth.dao;

import com.assignment.auth.exception.UserAlreadyExistException;
import com.assignment.auth.model.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> getUserByEmail(String email);
}
