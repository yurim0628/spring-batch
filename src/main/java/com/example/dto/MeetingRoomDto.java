package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MeetingRoomDto {
    private String branchName;
    private String location;
    private int roomNumber;
    private int capacity;
    private boolean hasProjector;
    private boolean canVideoConference;
}
