package com.example.healthboy.schedule.controller;

import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.schedule.dto.ScheduleCreateDto;
import com.example.healthboy.schedule.dto.ScheduleDto;
import com.example.healthboy.schedule.dto.ScheduleUpdateDto;
import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.schedule.service.ScheduleService;
import com.example.healthboy.timeblock.entity.TimeBlock;
import com.example.healthboy.timeblock.service.TimeBlockService;
import com.example.healthboy.user.dto.ProfileDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TimeBlockService timeBlockService;

    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(HttpServletRequest request,
            @RequestBody ScheduleCreateDto scheduleCreateDto) {
        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();

        Schedule createdSchedule = scheduleService.createSchedule(scheduleCreateDto, profile);

        ScheduleDto scheduleDto = new ScheduleDto(createdSchedule.getUrl(), createdSchedule.getName(),
                createdSchedule.getDescription());

        return ResponseEntity.ok(scheduleDto);
    }

    @PostMapping("/{url}/join")
    public ResponseEntity<String> joinSchedule(HttpServletRequest request, @PathVariable String url)
            throws ApplicationException {
        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();
        Schedule schedule = scheduleService.getSchedule(url);
        scheduleService.joinSchedule(schedule, profile);
        return ResponseEntity.ok("Join schedule successfully");
    }

    @GetMapping("/{url}")
    public ResponseEntity<ScheduleDto> getSchedule(@PathVariable String url) {
        Schedule schedule = scheduleService.getSchedule(url);

        ScheduleDto scheduleDto = new ScheduleDto(schedule.getUrl(), schedule.getName(),
                schedule.getDescription());
        return ResponseEntity.ok(scheduleDto);
    }

    @GetMapping("/{url}/users")
    public ResponseEntity<List<ProfileDto>> getScheduleMember(@PathVariable String url) {
        Schedule schedule = scheduleService.getSchedule(url);
        List<Profile> profiles = scheduleService.getScheduleMembers(schedule);

        List<ProfileDto> profileDtos = profiles.stream().map(ProfileDto::new).toList();

        return ResponseEntity.ok(profileDtos);
    }

    @GetMapping("/{url}/time-blocks")
    public ResponseEntity<List<TimeBlock>> getTimeBlocks() {
        List<TimeBlock> timeBlocks = timeBlockService.getAllTimeBlocks();
        return ResponseEntity.ok(timeBlocks);
    }

    @Transactional
    @PutMapping("/{url}")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable String url,
            @RequestBody ScheduleUpdateDto scheduleUpdateDto) {
        Schedule schedule = scheduleService.getSchedule(url);
        Schedule updatedSchedule = scheduleService.updateSchedule(schedule, scheduleUpdateDto);
        ScheduleDto scheduleDto = new ScheduleDto(updatedSchedule);
        return ResponseEntity.ok(scheduleDto);
    }

    @Transactional
    @DeleteMapping("/{url}/leave")
    public ResponseEntity<String> leaveSchedule(HttpServletRequest request, @PathVariable String url) {

        // Get Info
        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();
        Schedule schedule = scheduleService.getSchedule(url);

        // Delete SP
        ScheduleProfile scheduleProfile = scheduleService.getScheduleProfile(schedule, profile);
        if (scheduleProfile != null) {
            scheduleService.deleteScheduleProfile(scheduleProfile);
        }

        // Delete Schedule when member 0
        long remainingProfiles = scheduleService.countScheduleProfile(schedule);
        if (remainingProfiles == 0) {
            schedule.setDeletedAt(LocalDateTime.now());
        }
        return ResponseEntity.ok("Leave scheudle successfully");
    }

    @Transactional
    @DeleteMapping("/{url}")
    public ResponseEntity<String> deleteSchedule(HttpServletRequest request, @PathVariable String url) {
        Schedule schedule = scheduleService.getSchedule(url);
        scheduleService.deleteSchedule(schedule);
        scheduleService.deleteScheduleProfile(schedule);
        return ResponseEntity.ok("Delete schedule successfully");
    }
}
