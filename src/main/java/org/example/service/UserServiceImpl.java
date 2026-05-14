package org.example.service;

import org.example.entity.UserEntity;
import org.example.model.UserDTO;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for user operations.
 */
@Service
public final class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Constructor for UserServiceImpl.
     *
     * @param userRepository the user repository
     */
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    /**
     * Creates a new user.
     *
     * @param userDTO the user data transfer object
     * @return the created user DTO
     */
    public UserDTO createUser(final UserDTO userDTO) {
        UserEntity entity = new UserEntity();
        entity.setUsername(userDTO.getUsername());
        entity.setEmail(userDTO.getEmail());
        // For simplicity, we are not handling password here; in a real app, you would hash it.
        UserEntity saved = userRepository.save(entity);
        return new UserDTO(saved.getId(), saved.getUsername(), saved.getEmail());
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return new UserDTO(entity.getId(), entity.getUsername(), entity.getEmail());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(entity -> new UserDTO(entity.getId(), entity.getUsername(), entity.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        entity.setUsername(userDTO.getUsername());
        entity.setEmail(userDTO.getEmail());
        UserEntity updated = userRepository.save(entity);
        return new UserDTO(updated.getId(), updated.getUsername(), updated.getEmail());
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
