package com.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_room_id")
    private Long id;

    private int roomNumber;
    private int capacity;
    private boolean hasProjector;
    private boolean canVideoConference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Builder
    public MeetingRoom(
            int roomNumber,
            int capacity,
            boolean hasProjector,
            boolean canVideoConference
    ) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
        this.canVideoConference = canVideoConference;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
