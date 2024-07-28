package com.example.healthboy.timeblock.dto;

import java.sql.Timestamp;

public class TimeBlockMergeDto {

    private Timestamp startTime;
    private Timestamp endTime;
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
