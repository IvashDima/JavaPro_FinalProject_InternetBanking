package org.example.springbank.enums;

public enum TransactionType {
    DEPOSIT, CONVERT, TRANSFER;

    public static TransactionType fromString(String input) {
        try {
            return TransactionType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public String toString(){
        return name().toLowerCase();
    }
}