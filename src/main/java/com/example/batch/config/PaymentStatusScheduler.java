package com.example.batch.config;


import com.example.batch.dto.TransactionStatus;
import com.example.batch.entity.Transaction;
import com.example.batch.respository.TransactionRepository;
import com.example.batch.service.PaymentStatusProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentStatusScheduler {

    private final TransactionRepository transactionRepository;
    private final PaymentStatusProducer producer;

    public PaymentStatusScheduler(TransactionRepository transactionRepository,
                                  PaymentStatusProducer producer) {
        this.transactionRepository = transactionRepository;
        this.producer = producer;
    }

    @Scheduled(fixedRate = 10000) // cek tiap 10 detik
    public void checkPendingPayments() {
        List<Transaction> pendingTxns = transactionRepository.findByStatus(TransactionStatus.PENDING);

        if (pendingTxns.isEmpty()) {
            System.out.println("No pending payments at " + System.currentTimeMillis());
        } else {
            pendingTxns.forEach(producer::sendPendingPayment);
        }
    }
}
