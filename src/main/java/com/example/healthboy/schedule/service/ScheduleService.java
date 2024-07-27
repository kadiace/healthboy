package com.example.healthboy.schedule.service;

import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.schedule.dto.ScheduleCreateDto;
import com.example.healthboy.schedule.dto.ScheduleUpdateDto;
import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.schedule.repository.ScheduleRepository;
import com.example.healthboy.schedule.repository.ScheduleProfileRepository;
import com.example.healthboy.user.entity.Profile;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleProfileRepository scheduleProfileRepository;

    @Transactional
    public Schedule createSchedule(ScheduleCreateDto scheduleCreateDto, Profile profile) {
        Schedule schedule = new Schedule();
        schedule.setUrl(generateUniqueUrl());
        schedule.setName(scheduleCreateDto.getName());
        schedule.setDescription(scheduleCreateDto.getDescription());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        ScheduleProfile scheduleProfile = new ScheduleProfile();
        scheduleProfile.setProfile(profile);
        scheduleProfile.setSchedule(savedSchedule);

        scheduleProfileRepository.save(scheduleProfile);

        return savedSchedule;
    }

    @Transactional
    public void joinSchedule(Schedule schedule, Profile profile) {

        // Check if the ScheduleProfile already exists
        if (scheduleProfileRepository.existsByScheduleAndProfile(schedule, profile)) {
            throw new ApplicationException("Profile is already part of this schedule", HttpStatus.BAD_REQUEST);
        }
        ScheduleProfile scheduleProfile = new ScheduleProfile();
        scheduleProfile.setSchedule(schedule);
        scheduleProfile.setProfile(profile);
        scheduleProfileRepository.save(scheduleProfile);
    }

    public Schedule getSchedule(String url) {
        Schedule schedule = scheduleRepository.findByUrl(url);
        if (schedule == null) {
            throw new ApplicationException("Schedule not found", HttpStatus.BAD_REQUEST);
        }
        return schedule;
    };

    public List<Schedule> getMySchedules(Profile profile) {
        return scheduleProfileRepository.findSchedulesByProfile(profile);
    }

    public List<Profile> getScheduleMembers(Schedule schedule) {
        return scheduleProfileRepository.findProfilesBySchedule(schedule);
    }

    public ScheduleProfile getScheduleProfile(String url, Profile profile) {
        return scheduleProfileRepository.findByScheduleUrlAndProfile(url, profile);
    }

    public ScheduleProfile getScheduleProfile(Schedule schedule, Profile profile) {
        return scheduleProfileRepository.findByScheduleAndProfile(schedule, profile);
    }

    public long countScheduleProfile(Schedule schedule) {
        return scheduleProfileRepository.countBySchedule(schedule);
    }

    public Schedule updateSchedule(Schedule schedule, ScheduleUpdateDto scheduleUpdateDto) {
        schedule.setName(scheduleUpdateDto.getName());
        schedule.setDescription(scheduleUpdateDto.getDescription());
        return schedule;
    }

    public void deleteSchedule(Schedule schedule) {
        schedule.setDeletedAt(LocalDateTime.now());
    }

    public void deleteScheduleProfile(ScheduleProfile scheduleProfile) {
        scheduleProfileRepository.delete(scheduleProfile);
    }

    public void deleteScheduleProfile(Profile profile) {
        scheduleProfileRepository.deleteByProfile(profile);
    }

    public void deleteScheduleProfile(Schedule schedule) {
        scheduleProfileRepository.deleteBySchedule(schedule);
    }

    private String generateUniqueUrl() {
        return UUID.randomUUID().toString().substring(0, 10).replace("-", "");
    }
}
