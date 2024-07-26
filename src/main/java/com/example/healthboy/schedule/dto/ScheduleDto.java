package com.example.healthboy.schedule.dto;

import com.example.healthboy.schedule.entity.Schedule;

public class ScheduleDto {

    public ScheduleDto(String url, String name, String description) {
        this.url = url;
        this.name = name;
        this.description = description;
    }

    public ScheduleDto(Schedule schedule) {
        this.url = schedule.getUrl();
        this.name = schedule.getName();
        this.description = schedule.getDescription();

    }

    private String url;

    private String name;

    private String description;

    // Getters and setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
