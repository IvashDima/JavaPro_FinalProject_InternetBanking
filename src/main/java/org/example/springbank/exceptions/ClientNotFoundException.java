package org.example.springbank.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String name) {
        super("Client not found: " + name);
    }

    public ClientNotFoundException(Long id) {
        super("Client with ID '" + id + "' not found!");
    }

    public ClientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
