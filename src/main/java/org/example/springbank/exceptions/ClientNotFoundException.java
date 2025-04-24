package org.example.springbank.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String name) {
        super("Client with name " + name + "not found!");
    }
}
