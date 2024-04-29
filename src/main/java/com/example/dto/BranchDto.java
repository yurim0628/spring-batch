package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BranchDto {
    private String branchName;
    private String address;
    private int floor;
    private String operatingHours;
    private int capacity;
    private int studioCount;
    private int meetingRoomCount;
}
