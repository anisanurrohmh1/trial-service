package com.example.batch.service;


import com.example.batch.entity.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentStatusProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PaymentStatusProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPendingPayment(Transaction txn) {
        // Kirim ID + deskripsi transaksi
        String message = "Pending payment: ID=" + txn.getId() + ", desc=" + txn.getDescription();
        kafkaTemplate.send("payment-status", txn.getId(), message);
        System.out.println("Sent pending payment to Kafka: " + message);
    }
}
