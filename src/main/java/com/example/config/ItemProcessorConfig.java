package com.example.config;

import com.example.dto.BranchDto;
import com.example.dto.CompanyDto;
import com.example.dto.EmployeeDto;
import com.example.dto.MeetingRoomDto;
import com.example.entity.Branch;
import com.example.entity.Company;
import com.example.entity.Employee;
import com.example.entity.MeetingRoom;
import com.example.repository.BranchRepository;
import com.example.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class ItemProcessorConfig {

    private final BranchRepository branchRepository;
    private final CompanyRepository companyRepository;

    @Bean
    public ItemProcessor<BranchDto, Branch> branchesProcessor() {
        return branchDto -> Branch.builder()
                .branchName(branchDto.getBranchName())
                .address(branchDto.getAddress())
                .floor(branchDto.getFloor())
                .operatingHours(branchDto.getOperatingHours())
                .capacity(branchDto.getCapacity())
                .studioCount(branchDto.getStudioCount())
                .meetingRoomCount(branchDto.getMeetingRoomCount())
                .build();
    }

    @Bean
    public ItemProcessor<MeetingRoomDto, MeetingRoom> meetingRoomsProcessor() {
        return meetingRoomDto -> {
            boolean hasProjector = !meetingRoomDto.isHasProjector();
            boolean canVideoConference = !meetingRoomDto.isCanVideoConference();

            MeetingRoom meetingRoom = MeetingRoom.builder()
                    .roomNumber(meetingRoomDto.getRoomNumber())
                    .capacity(meetingRoomDto.getCapacity())
                    .hasProjector(hasProjector)
                    .canVideoConference(canVideoConference)
                    .build();

            Branch branch = branchRepository.findByBranchName(meetingRoomDto.getBranchName());
            meetingRoom.setBranch(branch);

            return meetingRoom;
        };
    }

    @Bean
    public ItemProcessor<CompanyDto, Company> companiesProcessor() {
        return companyDto -> Company.builder()
                .companyName(companyDto.getCompanyName())
                .business(companyDto.getBusiness())
                .headCount(companyDto.getHeadCount())
                .contractStartDate(LocalDate.parse(companyDto.getContractStartDate()))
                .contractEndDate(LocalDate.parse(companyDto.getContractEndDate()))
                .representativeName(companyDto.getRepresentativeName())
                .representativeContact(companyDto.getRepresentativeContact())
                .contractManagerName(companyDto.getContractManagerName())
                .contractManagerContact(companyDto.getContractManagerContact())
                .build();
    }

    @Bean
    public ItemProcessor<EmployeeDto, Employee> employeesProcessor() {
        return employeeDto -> {
            Employee employee = Employee.builder()
                    .loginId(employeeDto.getLoginId())
                    .name(employeeDto.getName())
                    .nickname(employeeDto.getNickname())
                    .contact(employeeDto.getContact())
                    .address(employeeDto.getAddress())
                    .gender(employeeDto.getGender())
                    .age(employeeDto.getAge())
                    .email(employeeDto.getEmail())
                    .build();

            Company company = companyRepository.findByCompanyName(employeeDto.getCompany());
            employee.setCompany(company);

            return employee;
        };
    }
}
