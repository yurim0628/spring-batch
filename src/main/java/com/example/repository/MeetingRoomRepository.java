package com.example.repository;

import com.example.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
}
