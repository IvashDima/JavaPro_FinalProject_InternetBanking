package org.example.springbank.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("Account with ID " + id + " not found!");
    }
}
