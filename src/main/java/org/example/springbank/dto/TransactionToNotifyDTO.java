package org.example.springbank.dto;

import java.util.Date;

public class TransactionToNotifyDTO {
    private final String email;
    private final Date date;
    private final String text;

    public TransactionToNotifyDTO(String email, Date date, String text) {
        this.email = email;
        this.date = date;
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}
