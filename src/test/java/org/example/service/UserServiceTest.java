package org.example.service;

import org.example.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    @Test
    void testCreateUser() {
        UserService service = new UserService();
        User user = service.createUser("john", "john@example.com");
        assertEquals("john", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    void testGetUser() {
        UserService service = new UserService();
        service.createUser("john", "john@example.com");
        User user = service.getUser("john");
        assertEquals("john", user.getUsername());
    }

    @Test
    void testUserAlreadyExists() {
        UserService service = new UserService();
        service.createUser("john", "john@example.com");
        assertThrows(IllegalArgumentException.class, () ->
            service.createUser("john", "john2@example.com"));
    }

    @Test
    void testDeleteUser() {
        UserService service = new UserService();
        service.createUser("john", "john@example.com");
        service.deleteUser("john");
        assertThrows(RuntimeException.class, () -> service.getUser("john"));
    }
}
