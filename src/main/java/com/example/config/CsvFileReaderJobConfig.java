package com.example.config;

import com.example.dto.BranchDto;
import com.example.dto.MeetingRoomDto;
import com.example.entity.Branch;
import com.example.entity.MeetingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class CsvFileReaderJobConfig {

    public static final int CHUNK_SIZE = 100;

    @Bean
    public Job csvFileReaderJob(
            JobRepository jobRepository,
            Step branchesCsvFileReadStep,
            Step meetingRoomsCsvFileReadStep
    ) {
        return new JobBuilder("csvFileReaderJob", jobRepository)
                .start(branchesCsvFileReadStep)
                .next(meetingRoomsCsvFileReadStep)
                .build();
    }

    @Bean
    public Step branchesCsvFileReadStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<BranchDto> branchesReader,
            ItemProcessor<BranchDto, Branch> branchesProcessor,
            ItemWriter<Branch> branchesWriter
    ) {
        return new StepBuilder("branchesCsvFileReadStep", jobRepository)
                .<BranchDto, Branch>chunk(CHUNK_SIZE, transactionManager)
                .reader(branchesReader)
                .processor(branchesProcessor)
                .writer(branchesWriter)
                .build();
    }

    @Bean
    public Step meetingRoomsCsvFileReadStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<MeetingRoomDto> meetingRoomsReader,
            ItemProcessor<MeetingRoomDto, MeetingRoom> meetingRoomsProcessor,
            ItemWriter<MeetingRoom> meetingRoomsWriter
    ) {
        return new StepBuilder("meetingRoomsCsvFileReadStep", jobRepository)
                .<MeetingRoomDto, MeetingRoom>chunk(CHUNK_SIZE, transactionManager)
                .reader(meetingRoomsReader)
                .processor(meetingRoomsProcessor)
                .writer(meetingRoomsWriter)
                .build();
    }
}
