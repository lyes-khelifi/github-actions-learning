package org.example.service;

import org.example.model.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public User createUser(String username, String email) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("User already exists: " + username);
        }
        User user = new User(username, email);
        users.put(username, user);
        return user;
    }

    public User getUser(String username) {
        User user = users.get(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }
        return user;
    }

    public void deleteUser(String username) {
        users.remove(username);
    }

    public Map<String, User> getAllUsers() {
        return users;
    }
}
