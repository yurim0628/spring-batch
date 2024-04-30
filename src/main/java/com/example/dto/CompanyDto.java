package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDto {
    private String companyName;
    private String business;
    private int headCount;
    private String contractStartDate;
    private String contractEndDate;
    private String representativeName;
    private String representativeContact;
    private String contractManagerName;
    private String contractManagerContact;
}
