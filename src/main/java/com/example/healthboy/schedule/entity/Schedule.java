package com.example.healthboy.schedule.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Schedule {

    @Id
    private String url;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30)
    private String description;

    @OneToMany(mappedBy = "schedule")
    private Set<ScheduleProfile> scheduleProfiles = new HashSet<>();

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

    public Set<ScheduleProfile> getScheduleProfiles() {
        return scheduleProfiles;
    }

    public void setScheduleProfiles(Set<ScheduleProfile> scheduleProfiles) {
        this.scheduleProfiles = scheduleProfiles;
    }
}
