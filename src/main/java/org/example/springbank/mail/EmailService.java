package org.example.springbank.mail;

import org.example.springbank.dto.ToNotifyDTO;

public interface EmailService {
    void sendMessage(ToNotifyDTO task);
}
