package com.example.config;

import com.example.dto.BranchDto;
import com.example.dto.MeetingRoomDto;
import com.example.entity.Branch;
import com.example.entity.MeetingRoom;
import com.example.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ItemProcessorConfig {

    private final BranchRepository branchRepository;

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
}
