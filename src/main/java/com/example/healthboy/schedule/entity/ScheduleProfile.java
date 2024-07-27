package com.example.healthboy.schedule.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.healthboy.timeblock.entity.TimeBlock;
import com.example.healthboy.user.entity.Profile;

import jakarta.persistence.*;

@Entity
@Table(name = "schedule_profile")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "ScheduleProfile.withScheduleAndProfile", attributeNodes = {
                @NamedAttributeNode("schedule"),
                @NamedAttributeNode("profile")
        })
})
public class ScheduleProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_url", nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @OneToMany(mappedBy = "scheduleProfile", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>();

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<TimeBlock> getTimeBlocks() {
        return timeBlocks;
    }

    public void setTimeBlocks(List<TimeBlock> timeBlocks) {
        this.timeBlocks = timeBlocks;
    }
}
