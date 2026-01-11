package com.example.batch.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledProducer {

    private final MessageProducer messageProducer;

    public ScheduledProducer(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Scheduled(fixedRate = 10000)
    public void sendScheduledMessage() {
        String message = "Hello Kafka! Time: " + LocalDateTime.now();
        messageProducer.sendMessage("my-topic", message);
    }
}
