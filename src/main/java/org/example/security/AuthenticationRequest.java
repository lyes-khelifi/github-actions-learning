package org.example.security;

/**
 * Represents an authentication request containing username and password.
 */
public final class AuthenticationRequest {
    /**
     * The username for authentication.
     */
    private String username;
    /**
     * The password for authentication.
     */
    private String password;

    /**
     * Default constructor.
     */
    public AuthenticationRequest() {}

    /**
     * Constructs an authentication request with the given credentials.
     *
     * @param username the username
     * @param password the password
     */
    public AuthenticationRequest(final String username, final String password) {
        this.username = username;
        this.password = password;
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
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}
