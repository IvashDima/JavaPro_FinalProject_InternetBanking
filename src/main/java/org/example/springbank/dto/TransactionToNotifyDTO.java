package org.example.springbank.dto;

import java.util.Date;

public class TransactionToNotifyDTO {
    private final String email;
    private final Date date;
    private final String senderName;
    private final String receiverName;
    private final double amount;
//    private final String type;

    public TransactionToNotifyDTO(String email, Date date, String senderName, String receiverName, double amount) {
        this.email = email;
        this.date = date;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.amount = amount;
//        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public Date getDate() {
        return date;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public double getAmount() {
        return amount;
    }

//    public String getType() {
//        return type;
//    }
}
