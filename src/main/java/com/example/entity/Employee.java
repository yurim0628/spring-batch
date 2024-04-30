package com.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    private String loginId;
    private String name;
    private String nickname;
    private String contact;
    private String address;
    private String gender;
    private int age;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public Employee(
            String loginId,
            String nickname,
            String name,
            String contact,
            String address,
            String gender,
            int age,
            String email
    ) {
        this.loginId = loginId;
        this.name = name;
        this.nickname = nickname;
        this.contact = contact;
        this.address = address;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    public void setCompany(Company company){
        this.company = company;
    }
}
