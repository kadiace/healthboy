package com.example.healthboy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.service.ScheduleService;
import com.example.healthboy.user.dto.UserUpdateDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable Long id,
            @Valid @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateProfile(id, userUpdateDto);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<Schedule>> getMySchedules(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Profile profile = user.getProfile();
        return ResponseEntity.ok(scheduleService.getMySchedules(profile));
    }
}
