package com.airline.airline.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.airline.airline.event.UserRegisteredEvent;

@Service
public class NotificationProducer {

    private static final Logger log = LoggerFactory.getLogger(NotificationProducer.class);
    private final String userRegisteredTopic;

    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

    public NotificationProducer(
            KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate,
            @Value("${app.kafka.topics.user-registered:user-registered-topic}") String userRegisteredTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.userRegisteredTopic = userRegisteredTopic;
    }

    public void publishUserRegisteredEvent(UserRegisteredEvent event) {
        try {
            kafkaTemplate.send(userRegisteredTopic, event)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.warn("Failed to publish event to topic {}: {}", userRegisteredTopic, ex.getMessage());
                        }
                    });
        } catch (RuntimeException ex) {
            log.warn("Kafka publish failed (broker/topic unavailable): {}", ex.getMessage());
        }
    }
}