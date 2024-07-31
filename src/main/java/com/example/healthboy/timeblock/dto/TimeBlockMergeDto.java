package com.example.healthboy.timeblock.dto;

import java.sql.Timestamp;

import com.example.healthboy.common.decorator.ThirtyMinuteInterval;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TimeBlockMergeDto {

    @NotNull(message = "Start time must exist")
    @ThirtyMinuteInterval
    private Timestamp startTime;

    @NotNull(message = "End time must exist")
    @ThirtyMinuteInterval
    private Timestamp endTime;

    @NotBlank(message = "Delete id must not be null")
    private String deleteId;

    // Getters and setters
    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public String getDeleteId() {
        return deleteId;
    }
}
