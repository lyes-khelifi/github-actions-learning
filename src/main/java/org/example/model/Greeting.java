package org.example.model;

/**
 * Simple DTO representing a greeting message.
 */
public final class Greeting {
    /**
     * The greeting message.
     */
    private String message;

    public Greeting(String message) {
        this.message = message;
    }

        /**
     * Returns the message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
