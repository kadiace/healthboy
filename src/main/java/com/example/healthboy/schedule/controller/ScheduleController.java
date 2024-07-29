package com.example.healthboy.schedule.controller;

import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.schedule.dto.ScheduleCreateDto;
import com.example.healthboy.schedule.dto.ScheduleDto;
import com.example.healthboy.schedule.dto.ScheduleUpdateDto;
import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.schedule.service.ScheduleService;
import com.example.healthboy.timeblock.dto.TimeBlockDto;
import com.example.healthboy.timeblock.entity.TimeBlock;
import com.example.healthboy.timeblock.service.TimeBlockService;
import com.example.healthboy.user.dto.ProfileDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TimeBlockService timeBlockService;

    @Transactional
    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(HttpServletRequest request,
            @RequestBody ScheduleCreateDto scheduleCreateDto) {

        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();

        Schedule createdSchedule = scheduleService.createSchedule(scheduleCreateDto, profile);
        scheduleService.createScheduleProfile(createdSchedule, profile);

        ScheduleDto scheduleDto = new ScheduleDto(createdSchedule);

        return ResponseEntity.ok(scheduleDto);
    }

    @PostMapping("/{url}/join")
    public ResponseEntity<String> joinSchedule(HttpServletRequest request, @PathVariable String url) {

        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();
        Schedule schedule = scheduleService.getSchedule(url);

        // Check if the ScheduleProfile already exists
        if (scheduleService.existScheduleProfile(schedule, profile)) {
            throw new ApplicationException("Profile is already part of this schedule", HttpStatus.BAD_REQUEST);
        }

        scheduleService.createScheduleProfile(schedule, profile);
        return ResponseEntity.ok("Join schedule successfully");
    }

    @GetMapping("/{url}")
    public ResponseEntity<ScheduleDto> getSchedule(HttpServletRequest request) {

        ScheduleProfile scheduleProfile = (ScheduleProfile) request.getAttribute("scheduleProfile");
        Schedule schedule = scheduleProfile.getSchedule();

        ScheduleDto scheduleDto = new ScheduleDto(schedule);

        return ResponseEntity.ok(scheduleDto);
    }

    @GetMapping("/{url}/users")
    public ResponseEntity<List<ProfileDto>> getScheduleMember(HttpServletRequest request) {

        ScheduleProfile scheduleProfile = (ScheduleProfile) request.getAttribute("scheduleProfile");
        Schedule schedule = scheduleProfile.getSchedule();

        List<Profile> profiles = scheduleService.getScheduleMembers(schedule);

        List<ProfileDto> profileDtos = profiles.stream().map(ProfileDto::new).toList();

        return ResponseEntity.ok(profileDtos);
    }

    @GetMapping("/{url}/time-blocks")
    public ResponseEntity<List<TimeBlockDto>> getTimeBlocks(HttpServletRequest request,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String end) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        Timestamp startTime = Timestamp.valueOf(LocalDateTime.parse(start, formatter));
        Timestamp endTime = Timestamp.valueOf(LocalDateTime.parse(end, formatter));

        // Get Request Attributes
        ScheduleProfile scheduleProfile = (ScheduleProfile) request.getAttribute("scheduleProfile");

        // Get Time Blocks
        List<TimeBlock> timeBlocks = timeBlockService.getTimeBlocks(scheduleProfile, startTime, endTime);

        // Parse
        List<TimeBlockDto> timeBlockDtos = timeBlocks.stream().map(timeBlock -> {
            TimeBlockDto timeBlockDto = new TimeBlockDto(timeBlock);
            timeBlockDto.setProfile(timeBlock.getScheduleProfile().getProfile());
            return timeBlockDto;
        }).toList();

        return ResponseEntity.ok(timeBlockDtos);
    }

    @Transactional
    @PutMapping("/{url}")
    public ResponseEntity<ScheduleDto> updateSchedule(HttpServletRequest request,
            @RequestBody ScheduleUpdateDto scheduleUpdateDto) {

        ScheduleProfile scheduleProfile = (ScheduleProfile) request.getAttribute("scheduleProfile");
        Schedule schedule = scheduleProfile.getSchedule();

        Schedule updatedSchedule = scheduleService.updateSchedule(schedule, scheduleUpdateDto);

        ScheduleDto scheduleDto = new ScheduleDto(updatedSchedule);

        return ResponseEntity.ok(scheduleDto);
    }

    @Transactional
    @DeleteMapping("/{url}/leave")
    public ResponseEntity<String> leaveSchedule(HttpServletRequest request) {

        // Delete SP
        ScheduleProfile scheduleProfile = (ScheduleProfile) request.getAttribute("scheduleProfile");
        scheduleService.deleteScheduleProfile(scheduleProfile);

        // Delete Schedule when member 0
        Schedule schedule = scheduleProfile.getSchedule();
        long remainingProfiles = scheduleService.countScheduleProfile(schedule);
        if (remainingProfiles == 0) {
            schedule.setDeletedAt(LocalDateTime.now());
        }

        return ResponseEntity.ok("Leave scheudle successfully");
    }

    @Transactional
    @DeleteMapping("/{url}")
    public ResponseEntity<String> deleteSchedule(HttpServletRequest request) {

        ScheduleProfile scheduleProfile = (ScheduleProfile) request.getAttribute("scheduleProfile");
        Schedule schedule = scheduleProfile.getSchedule();

        scheduleService.deleteSchedule(schedule);
        scheduleService.deleteScheduleProfiles(schedule);

        return ResponseEntity.ok("Delete schedule successfully");
    }
}
