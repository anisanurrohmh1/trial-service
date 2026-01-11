package com.example.batch.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Job starting: " + jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Job finished with status: " + jobExecution.getStatus());

        jobExecution.getStepExecutions().forEach(step ->
                System.out.println("Step " + step.getStepName() +
                        " readCount=" + step.getReadCount() +
                        ", writeCount=" + step.getWriteCount() +
                        ", skipCount=" + step.getSkipCount())
        );
    }
}
