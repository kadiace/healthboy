package com.example.healthboy.schedule.service;

import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.schedule.repository.ScheduleRepository;
import com.example.healthboy.schedule.repository.ScheduleProfileRepository;
import com.example.healthboy.user.entity.Profile;

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
    private ScheduleProfileRepository scheduleProfileRepository;

    @Transactional
    public Schedule createSchedule(Schedule schedule, Profile profile) {
        schedule.setUrl(generateUniqueUrl());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        ScheduleProfile scheduleProfile = new ScheduleProfile();
        scheduleProfile.setSchedule(savedSchedule);
        scheduleProfile.setProfile(profile);
        scheduleProfileRepository.save(scheduleProfile);
        return savedSchedule;
    }

    public List<Schedule> getMySchedules(Profile profile) {
        return scheduleProfileRepository.findSchedulesByProfile(profile);
    }

    public Schedule getSchedule(String id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    @Transactional
    public void joinSchedule(String id, Profile profile) {
        Schedule schedule = getSchedule(id);
        ScheduleProfile scheduleProfile = new ScheduleProfile();
        scheduleProfile.setSchedule(schedule);
        scheduleProfile.setProfile(profile);
        scheduleProfileRepository.save(scheduleProfile);
    }

    @Transactional
    public void leaveSchedule(String id, Profile profile) {
        Schedule schedule = getSchedule(id);
        ScheduleProfile scheduleProfile = scheduleProfileRepository.findByScheduleAndProfile(schedule, profile);
        scheduleProfileRepository.delete(scheduleProfile);
    }

    private String generateUniqueUrl() {
        return UUID.randomUUID().toString().substring(0, 10).replace("-", "");
    }
}
