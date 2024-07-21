package com.example.healthboy.schedule.controller;

import com.example.healthboy.schedule.entity.Schedule;
import com.example.healthboy.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule, Principal principal) {
        Schedule createdSchedule = scheduleService.createSchedule(schedule, principal.getName());
        return ResponseEntity.ok(createdSchedule);
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getMySchedules(Principal principal) {
        List<Schedule> schedules = scheduleService.getMySchedules(principal.getName());
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable String id) {
        Schedule schedule = scheduleService.getSchedule(id);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> joinSchedule(@PathVariable String id, Principal principal) {
        scheduleService.joinSchedule(id, principal.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> leaveSchedule(@PathVariable String id, Principal principal) {
        scheduleService.leaveSchedule(id, principal.getName());
        return ResponseEntity.ok().build();
    }
}
