package com.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long id;

    private String branchName;
    private String address;
    private int floor;
    private String operatingHours;
    private int capacity;
    private int studioCount;
    private int meetingRoomCount;

    @OneToMany(
            mappedBy = "branch",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    private List<MeetingRoom> meetingRooms;

    @Builder
    public Branch(
            String branchName,
            String address,
            int floor,
            String operatingHours,
            int capacity,
            int studioCount,
            int meetingRoomCount
    ) {
        this.branchName = branchName;
        this.address = address;
        this.floor = floor;
        this.operatingHours = operatingHours;
        this.capacity = capacity;
        this.studioCount = studioCount;
        this.meetingRoomCount = meetingRoomCount;
    }
}
