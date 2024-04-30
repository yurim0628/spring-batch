package com.example.config;

import com.example.entity.Branch;
import com.example.entity.Company;
import com.example.entity.Employee;
import com.example.entity.MeetingRoom;
import com.example.repository.BranchRepository;
import com.example.repository.CompanyRepository;
import com.example.repository.EmployeeRepository;
import com.example.repository.MeetingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class ItemWriterConfig {

    private final BranchRepository branchRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @Bean
    @Transactional
    public ItemWriter<Branch> branchesWriter() {
        return branches -> {
            for (Branch branch : branches) {
                branchRepository.save(branch);
            }
        };
    }

    @Bean
    @Transactional
    public ItemWriter<MeetingRoom> meetingRoomsWriter() {
        return meetingRooms -> {
            for (MeetingRoom meetingRoom : meetingRooms) {
                meetingRoomRepository.save(meetingRoom);
            }
        };
    }

    @Bean
    @Transactional
    public ItemWriter<Company> companiesWriter() {
        return companies -> {
            for (Company company : companies) {
                companyRepository.save(company);
            }
        };
    }

    @Bean
    @Transactional
    public ItemWriter<Employee> employeesWriter() {
        return employees -> {
            for (Employee employee : employees) {
                employeeRepository.save(employee);
            }
        };
    }
}
