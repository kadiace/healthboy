package com.example.healthboy.schedule.controller;

import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.service.ScheduleService;
import com.example.healthboy.user.entity.Profile;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(HttpServletRequest request, @RequestBody Schedule schedule) {
        Profile profile = (Profile) request.getAttribute("profile");
        Schedule createdSchedule = scheduleService.createSchedule(schedule, profile);
        return ResponseEntity.ok(createdSchedule);
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getMySchedules(HttpServletRequest request) {
        Profile profile = (Profile) request.getAttribute("profile");
        List<Schedule> schedules = scheduleService.getMySchedules(profile);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable String id) {
        Schedule schedule = scheduleService.getSchedule(id);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> joinSchedule(HttpServletRequest request, @PathVariable String id) {
        Profile profile = (Profile) request.getAttribute("profile");
        scheduleService.joinSchedule(id, profile);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> leaveSchedule(HttpServletRequest request, @PathVariable String id) {
        Profile profile = (Profile) request.getAttribute("profile");
        scheduleService.leaveSchedule(id, profile);
        return ResponseEntity.ok().build();
    }
}
