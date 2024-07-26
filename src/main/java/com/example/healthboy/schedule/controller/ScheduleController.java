package com.example.healthboy.schedule.controller;

import com.example.healthboy.schedule.dto.ScheduleCreateDto;
import com.example.healthboy.schedule.dto.ScheduleDto;
import com.example.healthboy.schedule.dto.ScheduleUpdateDto;
import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.service.ScheduleService;
import com.example.healthboy.user.dto.ProfileDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

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

    @PostMapping("/join/{url}")
    public ResponseEntity<String> joinSchedule(HttpServletRequest request, @PathVariable String url)
            throws BadRequestException {
        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();
        scheduleService.joinSchedule(url, profile);
        return ResponseEntity.ok("Join schedule successfully");
    }

    @GetMapping("/{url}")
    public ResponseEntity<ScheduleDto> getSchedule(@PathVariable String url) throws BadRequestException {
        Schedule schedule = scheduleService.getSchedule(url);

        ScheduleDto scheduleDto = new ScheduleDto(schedule.getUrl(), schedule.getName(),
                schedule.getDescription());
        return ResponseEntity.ok(scheduleDto);
    }

    @GetMapping("/users/{url}")
    public ResponseEntity<List<ProfileDto>> getScheduleMember(@PathVariable String url) throws BadRequestException {
        List<Profile> profiles = scheduleService.getScheduleMember(url);

        List<ProfileDto> profileDtos = profiles.stream().map(ProfileDto::new).toList();

        return ResponseEntity.ok(profileDtos);
    }

    @PutMapping("/{url}")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable String url,
            @RequestBody ScheduleUpdateDto scheduleUpdateDto) throws BadRequestException {
        Schedule updatedSchedule = scheduleService.updateSchedule(url, scheduleUpdateDto);

        ScheduleDto scheduleDto = new ScheduleDto(updatedSchedule.getUrl(), updatedSchedule.getName(),
                updatedSchedule.getDescription());

        return ResponseEntity.ok(scheduleDto);
    }

    @DeleteMapping("/leave/{url}")
    public ResponseEntity<String> leaveSchedule(HttpServletRequest request, @PathVariable String url)
            throws BadRequestException {
        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();
        scheduleService.leaveSchedule(url, profile);
        return ResponseEntity.ok("Leave scheudle successfully");
    }

    @DeleteMapping("/{url}")
    public ResponseEntity<String> deleteSchedule(HttpServletRequest request, @PathVariable String url)
            throws BadRequestException {
        scheduleService.deleteSchedule(url);
        return ResponseEntity.ok("Delete schedule successfully");
    }
}
