package com.example.healthboy.schedule.repository;

import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    List<Schedule> findByUsersContains(User user);
}
