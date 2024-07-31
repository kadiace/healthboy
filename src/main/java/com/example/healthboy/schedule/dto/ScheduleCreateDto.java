package com.example.healthboy.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ScheduleCreateDto {

    @NotBlank(message = "Schedule name must not be null")
    @Size(max = 20, message = "Name must be under 20 characters")
    private String name;

    @Size(max = 30, message = "Description must be under 30 characters")
    private String description;

    // Getters and setters
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
