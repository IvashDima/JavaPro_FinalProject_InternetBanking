package org.example.springbank.mail;

import org.example.springbank.dto.TransactionToNotifyDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender emailSender;
    private final ApplicationContext applicationContext;

    public EmailServiceImpl(JavaMailSender emailSender, ApplicationContext applicationContext) {
        this.emailSender = emailSender;
        this.applicationContext = applicationContext;
    }

    public void sendMessage(TransactionToNotifyDTO transaction) {
        SimpleMailMessage message = applicationContext.getBean(SimpleMailMessage.class);

        String text = String.format(message.getText(), transaction.getDate(), transaction.getText());

        message.setText(text);
        message.setTo(transaction.getEmail());

        emailSender.send(message);
    }
}
