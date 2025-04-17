package org.example.springbank.mail;

import org.example.springbank.dto.ToNotifyDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class EmailScheduler {
    private final EmailService emailService;

    public EmailScheduler(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(fixedDelay = 60000)
    public void sendNotifications() {
        List<ToNotifyDTO> tasks = emailService.getToNotify(new Date());
        tasks.forEach((task) -> emailService.sendMessage(task));
    }
}
