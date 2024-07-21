package com.example.healthboy.schedule.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.example.healthboy.user.entity.User;

@Entity
public class Schedule {

    @Id
    private String url;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30)
    private String description;

    @ManyToMany
    @JoinTable(name = "schedule_user", joinColumns = @JoinColumn(name = "schedule_url"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
