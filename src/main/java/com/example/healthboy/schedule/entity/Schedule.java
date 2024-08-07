package com.example.healthboy.schedule.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.healthboy.schedule.dto.ScheduleCreateDto;

@Entity
@SQLDelete(sql = "UPDATE user SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Schedule {

    public Schedule() {
    }

    public Schedule(ScheduleCreateDto scheduleCreateDto) {
        this.url = generateUniqueUrl();
        this.name = scheduleCreateDto.getName();
        this.description = scheduleCreateDto.getDescription();
    }

    @Id
    private String url;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30)
    private String description;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ScheduleProfile> scheduleProfiles = new ArrayList<ScheduleProfile>();

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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<ScheduleProfile> getScheduleProfiles() {
        return scheduleProfiles;
    }

    public void setScheduleProfiles(List<ScheduleProfile> scheduleProfiles) {
        this.scheduleProfiles = scheduleProfiles;
    }

    private String generateUniqueUrl() {
        return UUID.randomUUID().toString().substring(0, 10).replace("-", "");
    }
}
