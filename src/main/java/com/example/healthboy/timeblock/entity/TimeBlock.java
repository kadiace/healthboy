package com.example.healthboy.timeblock.entity;

import com.example.healthboy.schedule.entity.ScheduleUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TimeBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_user_id", nullable = false)
    private ScheduleUser ScheduleUser;

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

    public ScheduleUser getScheduleUser() {
        return ScheduleUser;
    }

    public void setScheduleUser(ScheduleUser ScheduleUser) {
        this.ScheduleUser = ScheduleUser;
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
