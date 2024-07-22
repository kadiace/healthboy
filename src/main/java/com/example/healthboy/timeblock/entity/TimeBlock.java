package com.example.healthboy.timeblock.entity;

import com.example.healthboy.schedule.entity.ScheduleProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TimeBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_profile_id", nullable = false)
    private ScheduleProfile scheduleProfile;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Integer blockCount;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScheduleProfile getScheduleProfile() {
        return scheduleProfile;
    }

    public void setScheduleProfile(ScheduleProfile scheduleProfile) {
        this.scheduleProfile = scheduleProfile;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(Integer blockCount) {
        this.blockCount = blockCount;
    }
}
