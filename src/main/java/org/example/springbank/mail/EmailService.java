package org.example.springbank.mail;

import org.example.springbank.dto.TransactionToNotifyDTO;

public interface EmailService {
    void sendMessage(TransactionToNotifyDTO transaction);
}
