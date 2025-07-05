package org.example.springbank.mail;

import org.example.springbank.dto.TransactionToNotifyDTO;
import org.example.springbank.services.TransactionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class EmailScheduler {
    private final EmailService emailService;

    private final TransactionService transactionService;

    public EmailScheduler(EmailService emailService, TransactionService transactionService) {
        this.emailService = emailService;
        this.transactionService = transactionService;
    }

    @Scheduled(fixedDelay = 60000)
    public void sendNotifications() {
        List<TransactionToNotifyDTO> transactions =
                transactionService.getTransactionToNotify(new Date());
        transactions.forEach((transaction) -> emailService.sendMessage(transaction));
    }
}
