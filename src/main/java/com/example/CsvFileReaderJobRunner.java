package com.example;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CsvFileReaderJobRunner {

    private final JobLauncher jobLauncher;
    private final Job csvFileReaderJob;

    @PostConstruct
    public void scheduleNotificationJob() throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("timestamp", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(csvFileReaderJob, jobParameters);
    }
}
