package com.example.batch.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentNotificationConsumer {

    @KafkaListener(topics = "payment-status", groupId = "payment-group")
    public void listen(String message) {
        System.out.println("Notify customer about pending payment: " + message);
    }
}
