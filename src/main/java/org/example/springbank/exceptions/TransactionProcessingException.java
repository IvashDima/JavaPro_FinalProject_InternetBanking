package org.example.springbank.exceptions;

public class TransactionProcessingException extends RuntimeException {
    public TransactionProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
