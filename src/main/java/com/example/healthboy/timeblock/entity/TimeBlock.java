package com.example.healthboy.timeblock.entity;

import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.timeblock.dto.TimeBlockCreateDto;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "TimeBlock.profile", attributeNodes = {
                @NamedAttributeNode(value = "scheduleProfile", subgraph = "scheduleProfile.profile")
        }, subgraphs = {
                @NamedSubgraph(name = "scheduleProfile.profile", attributeNodes = @NamedAttributeNode("profile"))
        }),
        @NamedEntityGraph(name = "TimeBlock.withScheduleProfile", attributeNodes = {
                @NamedAttributeNode(value = "scheduleProfile", subgraph = "scheduleProfile.withScheduleProfile"),
        }, subgraphs = {
                @NamedSubgraph(name = "scheduleProfile.withScheduleProfile", attributeNodes = {
                        @NamedAttributeNode("profile"), @NamedAttributeNode("schedule") }),
        })
})
public class TimeBlock {

    public TimeBlock(Timestamp startTime, Timestamp endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TimeBlock(TimeBlockCreateDto timeBlockCreateDto) {
        this.startTime = timeBlockCreateDto.getStartTime();
        this.endTime = timeBlockCreateDto.getEndTime();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_profile_id", nullable = false)
    private ScheduleProfile scheduleProfile;

    @Column(nullable = false)
    private Timestamp startTime;

    @Column(nullable = false)
    private Timestamp endTime;

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
}
