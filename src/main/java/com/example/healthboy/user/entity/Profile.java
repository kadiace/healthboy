package com.example.healthboy.user.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.healthboy.schedule.entity.ScheduleProfile;

import jakarta.persistence.*;

@Entity
@SQLDelete(sql = "UPDATE profile SET deleted_at = NOW() WHERE user_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Profile {

    @Id
    private Long id;

    @Column(length = 20, nullable = false)
    private String firstName;

    @Column(length = 20, nullable = false)
    private String lastName;

    private String profileImage;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private Set<ScheduleProfile> scheduleProfiles = new HashSet<>();

    private LocalDateTime deletedAt;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ScheduleProfile> getScheduleProfiles() {
        return scheduleProfiles;
    }

    public void setScheduleProfiles(Set<ScheduleProfile> scheduleProfiles) {
        this.scheduleProfiles = scheduleProfiles;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
