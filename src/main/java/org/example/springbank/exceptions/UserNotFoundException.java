package org.example.springbank.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String email) {
        super("User with email '" + email + "' not found!");
    }

    public UserNotFoundException(Long id) {
        super("User with ID '" + id + "' not found!");
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}