
package com.airline.airline.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.airline.airline.config.JwtUtil;
import com.airline.airline.event.UserRegisteredEvent;
import com.airline.airline.service.EmailService;

@Service
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    private final EmailService emailService;

    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.user-registered:user-registered-topic}",
            groupId = "${spring.kafka.consumer.group-id:airline-group}")
    public void handleUserRegistered(UserRegisteredEvent event) {
        if (event == null || event.getEmail() == null || event.getEmail().isBlank()) {
            log.warn("Received invalid UserRegisteredEvent: {}", event);
            return;
        }

        String token = JwtUtil.generateToken(event.getEmail());
        emailService.sendVerificationEmail(event.getEmail(), token);
    }
}
