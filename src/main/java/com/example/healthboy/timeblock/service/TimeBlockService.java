package com.example.healthboy.timeblock.service;

import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.timeblock.dto.TimeBlockCreateDto;
import com.example.healthboy.timeblock.dto.TimeBlockMergeDto;
import com.example.healthboy.timeblock.dto.TimeBlockUpdateDto;
import com.example.healthboy.timeblock.entity.TimeBlock;
import com.example.healthboy.timeblock.repository.TimeBlockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TimeBlockService {

    @Autowired
    private TimeBlockRepository timeBlockRepository;

    public TimeBlock createTimeBlock(TimeBlockCreateDto timeBlockCreateDto, ScheduleProfile scheduleProfile) {
        TimeBlock timeBlock = new TimeBlock(timeBlockCreateDto);
        timeBlock.setScheduleProfile(scheduleProfile);
        return timeBlockRepository.save(timeBlock);
    }

    public TimeBlock createTimeBlock(Timestamp startTime, Timestamp endTime, ScheduleProfile scheduleProfile) {
        TimeBlock timeBlock = new TimeBlock(startTime, endTime);
        timeBlock.setScheduleProfile(scheduleProfile);
        return timeBlockRepository.save(timeBlock);
    }

    public TimeBlock getTimeBlock(Long id) {
        return timeBlockRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Timeblock not found", HttpStatus.BAD_REQUEST));
    }

    public TimeBlock getTimeBlock(String id) {
        Long longId = Long.parseLong(id);
        return timeBlockRepository.findById(longId)
                .orElseThrow(() -> new ApplicationException("Timeblock not found", HttpStatus.BAD_REQUEST));
    }

    public List<TimeBlock> getTimeBlocks(ScheduleProfile scheduleProfile) {
        return timeBlockRepository.findBySP(scheduleProfile);
    }

    public List<TimeBlock> getTimeBlocks(ScheduleProfile scheduleProfile, Timestamp startTime, Timestamp endTime) {
        return timeBlockRepository.findBySPAndTimeRange(scheduleProfile, startTime, endTime);
    }

    public boolean checkTimeBlockOwner(TimeBlock timeBlock, long profileId) {
        return timeBlock.getScheduleProfile().getProfile().getId() == profileId;
    }

    public Long countTimeBlock(ScheduleProfile scheduleProfile, Timestamp startTime, Timestamp endTime) {
        return timeBlockRepository.countBySPAndTimeRange(scheduleProfile, startTime, endTime);
    }

    public TimeBlock updateTimeBlock(TimeBlock timeBlock, TimeBlockUpdateDto timeBlockUpdateDto) {
        timeBlock.setStartTime(timeBlockUpdateDto.getStartTime());
        timeBlock.setEndTime(timeBlockUpdateDto.getEndTime());
        return timeBlock;
    }

    public TimeBlock updateTimeBlock(TimeBlock timeBlock, TimeBlockMergeDto timeBlockMergeDto) {
        timeBlock.setStartTime(timeBlockMergeDto.getStartTime());
        timeBlock.setEndTime(timeBlockMergeDto.getEndTime());
        return timeBlock;
    }

    public TimeBlock updateTimeBlock(TimeBlock timeBlock, Timestamp startTime, Timestamp endTime) {
        timeBlock.setStartTime(startTime);
        timeBlock.setEndTime(endTime);
        return timeBlock;
    }

    public void deleteTimeBlock(Long id) {
        timeBlockRepository.deleteById(id);
    }

    public void deleteTimeBlock(TimeBlock timeBlock) {
        timeBlockRepository.delete(timeBlock);
    }
}
