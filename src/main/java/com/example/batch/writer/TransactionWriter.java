package com.example.batch.writer;

import com.example.batch.entity.Transaction;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;

@Component
public class TransactionWriter
        extends JpaItemWriter<Transaction> {

    public TransactionWriter(EntityManagerFactory emf) {
        setEntityManagerFactory(emf);
    }
}
