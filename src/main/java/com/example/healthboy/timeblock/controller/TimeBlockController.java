package com.example.healthboy.timeblock.controller;

import com.example.healthboy.timeblock.entity.TimeBlock;
import com.example.healthboy.timeblock.service.TimeBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-blocks")
public class TimeBlockController {

    @Autowired
    private TimeBlockService timeBlockService;

    @PostMapping
    public ResponseEntity<TimeBlock> createTimeBlock(@RequestBody TimeBlock timeBlock) {
        TimeBlock createdTimeBlock = timeBlockService.createTimeBlock(timeBlock);
        return ResponseEntity.ok(createdTimeBlock);
    }

    @GetMapping
    public ResponseEntity<List<TimeBlock>> getTimeBlocks() {
        List<TimeBlock> timeBlocks = timeBlockService.getAllTimeBlocks();
        return ResponseEntity.ok(timeBlocks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeBlock(@PathVariable Long id) {
        timeBlockService.deleteTimeBlock(id);
        return ResponseEntity.ok().build();
    }
}
