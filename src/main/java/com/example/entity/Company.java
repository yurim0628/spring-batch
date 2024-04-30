package com.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    private String companyName;
    private String business;
    private int headCount;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private String representativeName;
    private String representativeContact;
    private String contractManagerName;
    private String contractManagerContact;

    @OneToMany(
            mappedBy = "company",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private List<Employee> employees;

    @Builder
    public Company(
            String companyName,
            String business,
            int headCount,
            LocalDate contractStartDate,
            LocalDate contractEndDate,
            String representativeName,
            String representativeContact,
            String contractManagerName,
            String contractManagerContact
    ) {
        this.companyName = companyName;
        this.business = business;
        this.headCount = headCount;
        this.contractStartDate = contractStartDate;
        this.contractEndDate = contractEndDate;
        this.representativeName = representativeName;
        this.representativeContact = representativeContact;
        this.contractManagerName = contractManagerName;
        this.contractManagerContact = contractManagerContact;
    }
}
