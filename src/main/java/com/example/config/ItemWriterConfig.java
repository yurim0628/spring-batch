package com.example.config;

import com.example.entity.Branch;
import com.example.entity.MeetingRoom;
import com.example.repository.BranchRepository;
import com.example.repository.MeetingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class ItemWriterConfig {

    private final BranchRepository branchRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    @Bean
    @Transactional
    public ItemWriter<Branch> branchesWriter() {
        return branches -> {
            for (Branch branch : branches) {
                branchRepository.save(branch);
            }
        };
    }

    @Bean
    @Transactional
    public ItemWriter<MeetingRoom> meetingRoomsWriter() {
        return meetingRooms -> {
            for (MeetingRoom meetingRoom : meetingRooms) {
                meetingRoomRepository.save(meetingRoom);
            }
        };
    }
}
