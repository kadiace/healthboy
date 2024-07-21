package com.example.healthboy.schedule.repository;

import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.entity.ScheduleUser;
import com.example.healthboy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleUserRepository extends JpaRepository<ScheduleUser, Long> {
    ScheduleUser findByScheduleAndUser(Schedule schedule, User user);
}
