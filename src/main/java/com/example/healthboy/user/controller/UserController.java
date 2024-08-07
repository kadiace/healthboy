package com.example.healthboy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthboy.schedule.dto.ScheduleDto;
import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.service.ScheduleService;
import com.example.healthboy.user.dto.ProfileDto;
import com.example.healthboy.user.dto.ProfileUpdateDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();

        ProfileDto profileDto = new ProfileDto(profile);

        return ResponseEntity.ok(profileDto);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleDto>> getMySchedules(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();

        List<Schedule> schedules = scheduleService.getMySchedules(profile);

        List<ScheduleDto> scheduleDtos = schedules.stream().map(ScheduleDto::new).toList();

        return ResponseEntity.ok(scheduleDtos);
    }

    @PutMapping
    public ResponseEntity<ProfileDto> updateProfile(HttpServletRequest request,
            @Valid @RequestBody ProfileUpdateDto userUpdateDto) {

        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();
        Profile updatedProfile = userService.updateProfile(profile, userUpdateDto);

        ProfileDto profileDto = new ProfileDto(updatedProfile);

        return ResponseEntity.ok(profileDto);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {

        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();
        List<Schedule> affectedSchedules = scheduleService.getMySchedules(profile);
        List<String> affectedScheduleUrls = affectedSchedules.stream()
                .map(schedule -> schedule.getUrl())
                .toList();

        userService.deleteUser(user);
        userService.deleteProfile(profile);
        scheduleService.deleteScheduleProfiles(profile);
        scheduleService.deleteScheduleWithNoProfiles(affectedScheduleUrls);

        return ResponseEntity.ok("User delete successfully");
    }

}
