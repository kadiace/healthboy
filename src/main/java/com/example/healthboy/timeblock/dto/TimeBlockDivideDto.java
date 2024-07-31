package com.example.healthboy.timeblock.dto;

import java.sql.Timestamp;

import com.example.healthboy.common.decorator.ThirtyMinuteInterval;

import jakarta.validation.constraints.NotNull;

public class TimeBlockDivideDto {

    @NotNull(message = "Start time must exist")
    @ThirtyMinuteInterval
    private Timestamp startTime;

    @NotNull(message = "End time must exist")
    @ThirtyMinuteInterval
    private Timestamp endTime;

    // Getters and setters
    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }
}
