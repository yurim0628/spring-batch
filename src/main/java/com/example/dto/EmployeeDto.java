package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {
    private String loginId;
    private String name;
    private String nickname;
    private String company;
    private String contact;
    private String address;
    private String gender;
    private int age;
    private String email;
}
