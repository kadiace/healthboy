package com.example.healthboy.timeblock.dto;

import java.sql.Timestamp;

public class TimeBlockUpdateDto {

    private Timestamp startTime;
    private Timestamp endTime;

    // Getters and setters
    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }
}
