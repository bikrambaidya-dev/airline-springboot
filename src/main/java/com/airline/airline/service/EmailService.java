package com.airline.airline.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String token) {

        String verificationLink = "http://localhost:8080/api/auth/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verify your account");
        message.setText("Welcome to Airline System\n\n" + "Click below to verify your email:\n" + verificationLink);

        try {
            mailSender.send(message);
        } catch (MailException ex) {
            // log and continue — do not fail registration when mail can't be sent
            log.warn("Failed to send verification email to {}: {}", to, ex.getMessage());
        }
    }

}
