package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Represents a user entity persisted in the database.
 */
@Entity
@Table(name = "users")
public final class UserEntity {

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 50;
    private static final int MAX_EMAIL_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user.
     */
    @NotBlank
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    private String username;

    /**
     * The email address of the user.
     */
    @NotBlank
    @Email
    @Size(max = MAX_EMAIL_LENGTH)
    private String email;

    /**
     * The password of the user. Not validated — password handling is out of scope.
     */
    private String password;

    // Constructors
    public UserEntity() {}

    /**
     * Constructs a {@code UserEntity} with the specified username, email, and password.
     *
     * @param username the username
     * @param email the email address
     * @param password the password
     */
    public UserEntity(final String username, final String email, final String password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    /**
     * Returns the user ID.
     *
     * @return the user ID
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    public void setUsername(final String username)
    {
        this.username = username;
    }

    /**
     * Returns the email.
     *
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email to set
     */
    public void setEmail(final String email)
    {
        this.email = email;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(final String password)
    {
        this.password = password;
    }
}