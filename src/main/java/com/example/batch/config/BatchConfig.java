package com.example.batch.config;

import com.example.batch.dto.TransactionCsv;
import com.example.batch.entity.Transaction;
import com.example.batch.processor.TransactionProcessor;
import com.example.batch.reader.TransactionCsvReader;
import com.example.batch.writer.TransactionWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Step transactionStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            TransactionCsvReader reader,
            TransactionProcessor processor,
            TransactionWriter writer) {

        return new StepBuilder("transactionStep", jobRepository)
                .<TransactionCsv, Transaction>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importTransactionJob(
            JobRepository jobRepository,
            Step transactionStep) {

        return new JobBuilder("importTransactionJob", jobRepository)
                .start(transactionStep)
                .build();
    }
}
