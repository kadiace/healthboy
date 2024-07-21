package com.example.healthboy.schedule.service;

import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.entity.ScheduleUser;
import com.example.healthboy.schedule.repository.ScheduleRepository;
import com.example.healthboy.schedule.repository.ScheduleUserRepository;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.user.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleUserRepository scheduleUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Schedule createSchedule(Schedule schedule, String username) {
        User user = userRepository.findByEmail(username);
        schedule.setUrl(generateUniqueUrl());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        ScheduleUser scheduleUser = new ScheduleUser();
        scheduleUser.setSchedule(savedSchedule);
        scheduleUser.setUser(user);
        scheduleUserRepository.save(scheduleUser);
        return savedSchedule;
    }

    public List<Schedule> getMySchedules(String username) {
        User user = userRepository.findByEmail(username);
        return scheduleRepository.findByUsersContains(user);
    }

    public Schedule getSchedule(String id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    @Transactional
    public void joinSchedule(String id, String username) {
        User user = userRepository.findByEmail(username);
        Schedule schedule = getSchedule(id);
        ScheduleUser scheduleUser = new ScheduleUser();
        scheduleUser.setSchedule(schedule);
        scheduleUser.setUser(user);
        scheduleUserRepository.save(scheduleUser);
    }

    @Transactional
    public void leaveSchedule(String id, String username) {
        User user = userRepository.findByEmail(username);
        Schedule schedule = getSchedule(id);
        ScheduleUser scheduleUser = scheduleUserRepository.findByScheduleAndUser(schedule, user);
        scheduleUserRepository.delete(scheduleUser);
    }

    private String generateUniqueUrl() {
        return UUID.randomUUID().toString().substring(0, 10).replace("-", "");
    }
}
