package com.example.healthboy.timeblock.controller;

import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.timeblock.dto.TimeBlockCreateDto;
import com.example.healthboy.timeblock.dto.TimeBlockDivideDto;
import com.example.healthboy.timeblock.dto.TimeBlockDto;
import com.example.healthboy.timeblock.dto.TimeBlockMergeDto;
import com.example.healthboy.timeblock.dto.TimeBlockUpdateDto;
import com.example.healthboy.timeblock.entity.TimeBlock;
import com.example.healthboy.timeblock.service.TimeBlockService;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/time-blocks")
public class TimeBlockController {

    @Autowired
    private TimeBlockService timeBlockService;

    @PostMapping
    public ResponseEntity<TimeBlockDto> createTimeBlock(HttpServletRequest request,
            @RequestBody TimeBlockCreateDto timeBlockCreateDto) {

        ScheduleProfile scheduleProfile = (ScheduleProfile) request.getAttribute("scheduleProfile");

        TimeBlock createdTimeBlock = timeBlockService.createTimeBlock(timeBlockCreateDto, scheduleProfile);

        TimeBlockDto timeBlockDto = new TimeBlockDto(createdTimeBlock);

        return ResponseEntity.ok(timeBlockDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeBlockDto> updateTimeBlock(HttpServletRequest request,
            @RequestBody TimeBlockUpdateDto timeBlockUpdateDto) {

        TimeBlock timeBlock = (TimeBlock) request.getAttribute("timeBlock");

        TimeBlock updatedTimeBlock = timeBlockService.updateTimeBlock(timeBlock, timeBlockUpdateDto);

        TimeBlockDto timeBlockDto = new TimeBlockDto(updatedTimeBlock);

        return ResponseEntity.ok(timeBlockDto);
    }

    @Transactional
    @PutMapping("/{id}/merge")
    public ResponseEntity<TimeBlockDto> mergeTimeBlock(HttpServletRequest request,
            @RequestBody TimeBlockMergeDto timeBlockMergeDto) {

        TimeBlock timeBlock = (TimeBlock) request.getAttribute("timeBlock");
        Profile profile = ((User) request.getAttribute("user")).getProfile();

        // Get delete time block, check owner, delete
        TimeBlock deleteBlock = timeBlockService.getTimeBlock(timeBlockMergeDto.getDeleteId());
        if (!timeBlockService.checkTimeBlockOwner(deleteBlock, profile.getId())) {
            throw new ApplicationException("", HttpStatus.BAD_REQUEST);
        }
        timeBlockService.deleteTimeBlock(deleteBlock);

        // Update time block
        TimeBlock updatedTimeBlock = timeBlockService.updateTimeBlock(timeBlock, timeBlockMergeDto);

        // Parse
        TimeBlockDto timeBlockDto = new TimeBlockDto(updatedTimeBlock);

        return ResponseEntity.ok(timeBlockDto);
    }

    @Transactional
    @PutMapping("/{id}/divide")
    public ResponseEntity<List<TimeBlockDto>> divideTimeBlock(HttpServletRequest request,
            @RequestBody TimeBlockDivideDto timeBlockDivideDto) {

        TimeBlock timeBlock = (TimeBlock) request.getAttribute("timeBlock");
        Timestamp prevStartTime = timeBlock.getStartTime();
        Timestamp prevEndTime = timeBlockDivideDto.getStartTime();
        Timestamp postStartTime = timeBlockDivideDto.getEndTime();
        Timestamp postEndTime = timeBlock.getEndTime();

        // Check divide time is valid
        if (prevStartTime.before(prevEndTime)
                && postEndTime.after(postStartTime)) {
            throw new ApplicationException("{Here}", HttpStatus.BAD_REQUEST);
        }

        // Update time block
        TimeBlock updatedTimeBlock = timeBlockService.updateTimeBlock(timeBlock, prevStartTime,
                prevEndTime);

        // Create new time block
        TimeBlock newTimeBlock = timeBlockService.createTimeBlock(postStartTime, postEndTime,
                timeBlock.getScheduleProfile());

        // Parse
        ArrayList<TimeBlockDto> timeBlockDtos = new ArrayList<TimeBlockDto>();
        timeBlockDtos.add(new TimeBlockDto(updatedTimeBlock));
        timeBlockDtos.add(new TimeBlockDto(newTimeBlock));

        return ResponseEntity.ok(timeBlockDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimeBlock(@PathVariable Long id) {
        timeBlockService.deleteTimeBlock(id);
        return ResponseEntity.ok("Time block delete Successfully");
    }
}
