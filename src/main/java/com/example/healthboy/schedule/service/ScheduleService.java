package com.example.healthboy.schedule.service;

import com.example.healthboy.schedule.dto.ScheduleCreateDto;
import com.example.healthboy.schedule.dto.ScheduleUpdateDto;
import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.schedule.repository.ScheduleRepository;
import com.example.healthboy.schedule.repository.ScheduleProfileRepository;
import com.example.healthboy.user.entity.Profile;

import jakarta.transaction.Transactional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void joinSchedule(String url, Profile profile) throws BadRequestException {
        Schedule schedule = getSchedule(url);

        // Check if the ScheduleProfile already exists
        if (scheduleProfileRepository.existsByScheduleAndProfile(schedule, profile)) {
            throw new BadRequestException("Profile is already part of this schedule");
        }
        ScheduleProfile scheduleProfile = new ScheduleProfile();
        scheduleProfile.setSchedule(schedule);
        scheduleProfile.setProfile(profile);
        scheduleProfileRepository.save(scheduleProfile);
    }

    public Schedule getSchedule(String url) throws BadRequestException {
        return scheduleRepository.findById(url).orElseThrow(() -> new BadRequestException("Schedule not found"));
    }

    public List<Schedule> getMySchedules(Profile profile) throws BadRequestException {
        return scheduleProfileRepository.findSchedulesByProfile(profile);
    }

    public List<Profile> getScheduleMember(String url) throws BadRequestException {
        Schedule schedule = getSchedule(url);
        return scheduleProfileRepository.findProfilesBySchedule(schedule);
    }

    @Transactional
    public Schedule updateSchedule(String url, ScheduleUpdateDto scheduleUpdateDto) throws BadRequestException {
        Schedule schedule = getSchedule(url);
        schedule.setName(scheduleUpdateDto.getName());
        schedule.setDescription(scheduleUpdateDto.getDescription());
        return schedule;
    }

    @Transactional
    public void leaveSchedule(String url, Profile profile) throws BadRequestException {
        Schedule schedule = getSchedule(url);
        ScheduleProfile scheduleProfile = scheduleProfileRepository.findByScheduleAndProfile(schedule, profile);
        scheduleProfileRepository.delete(scheduleProfile);

        long remainingProfiles = scheduleProfileRepository.countBySchedule(schedule);
        if (remainingProfiles == 0) {
            schedule.setDeletedAt(LocalDateTime.now());
        }
    }

    @Transactional
    public void deleteSchedule(String url) throws BadRequestException {
        Schedule schedule = getSchedule(url);
        scheduleProfileRepository.deleteBySchedule(schedule);
        schedule.setDeletedAt(LocalDateTime.now());
    }

    private String generateUniqueUrl() {
        return UUID.randomUUID().toString().substring(0, 10).replace("-", "");
    }
}
