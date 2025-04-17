package org.example.springbank.mail;

import org.example.springbank.dto.ToNotifyDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailServiceImpl implements EmailService{

    private final JavaMailSender emailSender;
    private final ApplicationContext applicationContext;

    public EmailServiceImpl(JavaMailSender emailSender, ApplicationContext applicationContext) {
        this.emailSender = emailSender;
        this.applicationContext = applicationContext;
    }

    public void sendMessage(ToNotifyDTO task) {
        SimpleMailMessage message = applicationContext.getBean(SimpleMailMessage.class);

        String text = String.format(message.getText(), task.getDate(), task.getText());

        message.setText(text);
        message.setTo(task.getEmail());

        emailSender.send(message);
    }
}
