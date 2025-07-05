package org.example.springbank.exceptions;

public class AccountProcessingException extends RuntimeException {
    public AccountProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
