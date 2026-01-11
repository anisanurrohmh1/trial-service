package com.example.batch.respository;

import com.example.batch.dto.TransactionStatus;
import com.example.batch.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByStatus(TransactionStatus status);
}
