package org.example.model;

/**
 * Simple user representation.
 */
public final class User {
    /**
     * The username.
     */
    private String username;
    /**
     * The email address.
     */
    private String email;

    /**
     * Constructs a {@code User} with the specified username and email.
     *
     * @param username the username
     * @param email the email address
     */
    public User(final String username, final String email) {
        this.username = username;
        this.email = email;
    }

    /**
     * Returns the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Returns the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
