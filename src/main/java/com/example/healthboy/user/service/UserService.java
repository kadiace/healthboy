package com.example.healthboy.user.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.healthboy.user.dto.UserUpdateDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public ResponseEntity<String> updateProfile(Profile profile, UserUpdateDto userUpdateDto) {

        profile.setFirstName(userUpdateDto.getFirstName());
        profile.setLastName(userUpdateDto.getLastName());
        profile.setProfileImage(userUpdateDto.getProfileImage());
        entityManager.merge(profile);

        return ResponseEntity.ok("Profile update successfully");
    }

    @Transactional
    public ResponseEntity<String> deleteUser(User user) {
        user.setDeletedAt(LocalDateTime.now());
        user.getProfile().setDeletedAt(LocalDateTime.now());
        entityManager.merge(user);
        return ResponseEntity.ok("User delete successfully");
    }

}
