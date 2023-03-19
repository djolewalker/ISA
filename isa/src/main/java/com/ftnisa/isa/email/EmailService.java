package com.ftnisa.isa.email;

import com.ftnisa.isa.configuration.EmailConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final EmailConfiguration emailConfiguration;

    public EmailService(EmailConfiguration emailConfiguration) {
        this.emailConfiguration = emailConfiguration;
    }

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(emailConfiguration.getHost());
        mailSenderImpl.setPort(emailConfiguration.getPort());
        mailSenderImpl.setUsername(emailConfiguration.getUsername());
        mailSenderImpl.setPassword(emailConfiguration.getPassword());
        return mailSenderImpl;
    }

    public void sendSimpleMessage(String to, String from, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        getJavaMailSender().send(message);
    }
}