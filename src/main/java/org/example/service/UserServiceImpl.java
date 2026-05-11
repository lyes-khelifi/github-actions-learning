package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
            new RuntimeException("User not found: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
            .collect(Collectors.toList());
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUser(id);
        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}