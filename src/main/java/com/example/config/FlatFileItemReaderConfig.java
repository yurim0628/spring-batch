package com.example.config;

import com.example.dto.BranchDto;
import com.example.dto.CompanyDto;
import com.example.dto.EmployeeDto;
import com.example.dto.MeetingRoomDto;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FlatFileItemReaderConfig {

    private static final String BRANCHES_RESOURCE_PATH = "csv/branches.csv";
    private static final String MEETING_ROOMS_RESOURCE_PATH = "csv/meeting-rooms.csv";
    private static final String COMPANIES_RESOURCE_PATH = "csv/companies.csv";
    private static final String EMPLOYEES_RESOURCE_PATH = "csv/employees.csv";

    private static final String[] BRANCH_FIELD_NAMES = {"branchName", "address", "floor", "operatingHours", "capacity", "studioCount", "meetingRoomCount"};
    private static final String[] MEETING_ROOM_FIELD_NAMES = {"branchName", "location", "roomNumber", "capacity", "hasProjector", "canVideoConference"};
    private static final String[] COMPANY_FIELD_NAMES = {"companyName", "business", "headCount", "contractStartDate", "contractEndDate", "representativeName", "representativeContact", "contractManagerName", "contractManagerContact"};
    private static final String[] EMPLOYEE_FIELD_NAMES = {"loginId", "name", "nickname", "company", "contact", "address", "gender", "age", "email"};


    private <T> FlatFileItemReader<T> createFlatFileItemReader(String resourcePath, String[] fieldNames, Class<T> targetType) {
        ClassPathResource classPathResource = new ClassPathResource(resourcePath);

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(fieldNames);

        BeanWrapperFieldSetMapper<T> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(targetType);

        DefaultLineMapper<T> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return new FlatFileItemReaderBuilder<T>()
                .name("flatFileItemReader")
                .resource(classPathResource)
                .encoding("UTF-8")
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

    @Bean
    public FlatFileItemReader<CompanyDto> companiesReader() {
        return createFlatFileItemReader(COMPANIES_RESOURCE_PATH, COMPANY_FIELD_NAMES, CompanyDto.class);
    }

    @Bean
    public FlatFileItemReader<EmployeeDto> employeesReader() {
        return createFlatFileItemReader(EMPLOYEES_RESOURCE_PATH, EMPLOYEE_FIELD_NAMES, EmployeeDto.class);
    }
}
