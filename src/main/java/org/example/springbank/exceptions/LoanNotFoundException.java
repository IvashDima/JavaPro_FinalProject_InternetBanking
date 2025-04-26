package org.example.springbank.exceptions;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Long id) {
        super("Loan with ID " + id + " not found!");
    }
}