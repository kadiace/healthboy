package com.example.healthboy.schedule.repository;

import com.example.healthboy.schedule.entity.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    Schedule findByUrl(String url);
}
