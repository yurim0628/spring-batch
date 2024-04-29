package com.example.config;

import com.example.dto.BranchDto;
import com.example.dto.MeetingRoomDto;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class FlatFileItemReaderConfig {

    private static final String BRANCHES_RESOURCE_PATH = "csv/branches.csv";
    private static final String MEETING_ROOMS_RESOURCE_PATH = "csv/meeting-rooms.csv";

    private static final String[] BRANCH_FIELD_NAMES = {"branchName", "address", "floor", "operatingHours", "capacity", "studioCount", "meetingRoomCount"};
    private static final String[] MEETING_ROOM_FIELD_NAMES = {"branchName", "location", "roomNumber", "capacity", "hasProjector", "canVideoConference"};

    private <T> FlatFileItemReader<T> createFlatFileItemReader(String resourcePath, String[] fieldNames, Class<T> targetType) {
        BeanWrapperFieldSetMapper<T> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(targetType);

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(fieldNames);

        DefaultLineMapper<T> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return new FlatFileItemReaderBuilder<T>()
                .name("flatFileItemReader")
                .resource(new ClassPathResource(resourcePath))
                .encoding(UTF_8.name())
                .lineMapper(defaultLineMapper)
                .build();
    }

    @Bean
    public FlatFileItemReader<BranchDto> branchesReader() {
        return createFlatFileItemReader(BRANCHES_RESOURCE_PATH, BRANCH_FIELD_NAMES, BranchDto.class);
    }

    @Bean
    public FlatFileItemReader<MeetingRoomDto> meetingRoomsReader() {
        return createFlatFileItemReader(MEETING_ROOMS_RESOURCE_PATH, MEETING_ROOM_FIELD_NAMES, MeetingRoomDto.class);
    }
}
