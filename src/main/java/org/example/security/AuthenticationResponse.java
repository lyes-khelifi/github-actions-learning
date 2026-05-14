package org.example.security;

/**
 * Represents an authentication response containing a JWT token.
 */
public final class AuthenticationResponse {
    /**
     * The JWT token.
     */
    private final String jwt;

    /**
     * Constructs an authentication response with the given JWT token.
     *
     * @param jwt the JWT token
     */
    public AuthenticationResponse(final String jwt) {
        this.jwt = jwt;
    }

    /**
     * Returns the JWT token.
     *
     * @return the JWT token
     */
    public String getJwt() {
        return jwt;
    }
}
