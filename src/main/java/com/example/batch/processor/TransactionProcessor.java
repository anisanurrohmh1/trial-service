package com.example.batch.processor;

import com.example.batch.dto.TransactionCsv;
import com.example.batch.entity.Transaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TransactionProcessor
        implements ItemProcessor<TransactionCsv, Transaction> {

    @Override
    public Transaction process(TransactionCsv csv) {
        Transaction trx = new Transaction();
        trx.setId(csv.getId());
        trx.setDescription(csv.getDescription().toUpperCase());
        trx.setAmount(csv.getAmount());
        return trx;
    }
}
