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

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleProfileRepository scheduleProfileRepository;

    @Transactional
    public Schedule createSchedule(ScheduleCreateDto scheduleCreateDto, Profile profile) {
        Schedule schedule = new Schedule(scheduleCreateDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return savedSchedule;
    }

    @Transactional
    public ScheduleProfile createScheduleProfile(Schedule schedule, Profile profile) {
        ScheduleProfile scheduleProfile = new ScheduleProfile();
        scheduleProfile.setSchedule(schedule);
        scheduleProfile.setProfile(profile);
        ScheduleProfile createdScheduleProfile = scheduleProfileRepository.save(scheduleProfile);
        return createdScheduleProfile;
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

    public boolean existScheduleProfile(Schedule schedule, Profile profile) {
        return scheduleProfileRepository.existsByScheduleAndProfile(schedule, profile);
    }

    public Schedule updateSchedule(Schedule schedule, ScheduleUpdateDto scheduleUpdateDto) {
        schedule.setName(scheduleUpdateDto.getName());
        schedule.setDescription(scheduleUpdateDto.getDescription());
        return schedule;
    }

    public void deleteSchedule(Schedule schedule) {
        schedule.setDeletedAt(LocalDateTime.now());
    }

    public void deleteScheduleWithNoProfiles(List<String> scheduleUrls) {
        scheduleRepository.deleteSchedulesWithNoProfiles(scheduleUrls, LocalDateTime.now());
    }

    public void deleteScheduleProfile(ScheduleProfile scheduleProfile) {
        scheduleProfileRepository.delete(scheduleProfile);
    }

    public void deleteScheduleProfiles(Profile profile) {
        scheduleProfileRepository.deleteByProfile(profile);
    }

    public void deleteScheduleProfiles(Schedule schedule) {
        scheduleProfileRepository.deleteBySchedule(schedule);
    }

    public void deleteScheduleProfiles(List<ScheduleProfile> scheduleProfiles) {
        scheduleProfileRepository.deleteAll(scheduleProfiles);
    }
}
