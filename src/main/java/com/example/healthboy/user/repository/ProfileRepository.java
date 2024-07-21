package com.example.healthboy.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUser(User user);
}
