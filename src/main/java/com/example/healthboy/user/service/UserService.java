package com.example.healthboy.user.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.healthboy.user.dto.ProfileUpdateDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Transactional
    public Profile updateProfile(Profile profile, ProfileUpdateDto userUpdateDto) {
        profile.setFirstName(userUpdateDto.getFirstName());
        profile.setLastName(userUpdateDto.getLastName());
        profile.setProfileImage(userUpdateDto.getProfileImage());
        return profile;
    }

    @Transactional
    public void deleteUser(User user, LocalDateTime now) {
        user.setDeletedAt(now);
    }

    @Transactional
    public void deleteProfile(Profile profile, LocalDateTime now) {
        profile.setDeletedAt(now);
    }

}
