package org.example.model;

/**
 * Simple DTO representing a greeting message.
 */
public class Greeting {
    /**
     * The greeting message.
     */
    private String message;

    public Greeting(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}