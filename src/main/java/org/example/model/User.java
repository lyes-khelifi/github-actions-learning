package org.example.model;

/**
 * Simple user representation.
 */

/**
 * Simple user representation.
 */
public class User {
    /**
     * The username.
     */
    private String username;
    /**
     * The email address.
     */
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
