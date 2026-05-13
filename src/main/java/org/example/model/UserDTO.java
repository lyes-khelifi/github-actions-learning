/**
 * Data transfer object for user information.
 */
package org.example.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data transfer object for user information.
 */
public class UserDTO
{
    private Long id;

    /**
     * The username of the user.
     */
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    /**
     * The email address of the user.
     */
    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    // Constructors
    public UserDTO()
    {}

    /**
     * Constructs a {@code UserDTO} with the specified id, username, and email.
     *
     * @param id the user id
     * @param username the username
     * @param email the email address
     */
    public UserDTO(Long id, String username, String email)
    {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters and setters
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}