package com.assignment.auth.dao;

import com.assignment.auth.exception.UserAlreadyExistException;
import com.assignment.auth.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryUserRepository implements UserRepository{
    private Map<String,User> userMap;

    public InMemoryUserRepository() {
        userMap = new HashMap<>();
    }

    @Override
    public void save(User user) {
        userMap.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userMap.get(email));
    }
}
