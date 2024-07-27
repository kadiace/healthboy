package com.example.healthboy.user.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.healthboy.user.dto.ProfileUpdateDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    EntityManager entityManager;

    @Transactional
    public Profile updateProfile(Profile profile, ProfileUpdateDto userUpdateDto) {

        Profile persistentProfile = entityManager.contains(profile) ? profile
                : entityManager.find(Profile.class, profile.getId());

        persistentProfile.setFirstName(userUpdateDto.getFirstName());
        persistentProfile.setLastName(userUpdateDto.getLastName());
        persistentProfile.setProfileImage(userUpdateDto.getProfileImage());

        return persistentProfile;
    }

    @Transactional
    public void deleteUser(User user) {

        User persistentUser = entityManager.contains(user) ? user
                : entityManager.find(User.class, user.getId());
        persistentUser.setDeletedAt(LocalDateTime.now());
        persistentUser.getProfile().setDeletedAt(LocalDateTime.now());
    }

}
