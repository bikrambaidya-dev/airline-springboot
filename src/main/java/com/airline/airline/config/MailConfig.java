package com.airline.airline.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.username:}")
    private String username;

    @Value("${spring.mail.password:}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender(Environment env) {

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(env.getProperty("spring.mail.host", "smtp.gmail.com"));
        sender.setPort(Integer.parseInt(env.getProperty("spring.mail.port", "587")));

        // Prefer spring.mail.*; fallback to MAIL_USERNAME / MAIL_PASSWORD env vars
        String user = username != null && !username.isBlank() ? username : env.getProperty("MAIL_USERNAME", env.getProperty("SPRING_MAIL_USERNAME"));
        String pass = password != null && !password.isBlank() ? password : env.getProperty("MAIL_PASSWORD", env.getProperty("SPRING_MAIL_PASSWORD"));

        if (user != null && !user.isBlank()) {
            sender.setUsername(user);
        }
        if (pass != null && !pass.isBlank()) {
            sender.setPassword(pass);
        }

        Properties props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth", "true"));
        props.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable", "true"));

        return sender;
    }
}
