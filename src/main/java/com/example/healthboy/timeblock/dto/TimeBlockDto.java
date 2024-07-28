package com.example.healthboy.timeblock.dto;

import java.sql.Timestamp;

import com.example.healthboy.timeblock.entity.TimeBlock;
import com.example.healthboy.user.dto.ProfileDto;
import com.example.healthboy.user.entity.Profile;

public class TimeBlockDto {

    public TimeBlockDto(Long id, Timestamp startTime, Timestamp endTime, ProfileDto profile) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.profile = profile;
    }

    public TimeBlockDto(TimeBlock timeBlock) {
        this.id = timeBlock.getId();
        this.startTime = timeBlock.getStartTime();
        this.endTime = timeBlock.getEndTime();
        this.profile = new ProfileDto(timeBlock.getScheduleProfile().getProfile());
    }

    private Long id;
    private Timestamp startTime;
    private Timestamp endTime;
    private ProfileDto profile;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public ProfileDto getProfile() {
        return profile;
    }

    public void setProfile(ProfileDto profile) {
        this.profile = profile;
    }

    public void setProfile(Profile profile) {
        this.profile = new ProfileDto(profile);
    }

}
