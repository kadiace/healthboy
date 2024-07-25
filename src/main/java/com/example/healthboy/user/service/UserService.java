package com.example.healthboy.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.healthboy.user.dto.UserUpdateDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.repository.ProfileRepository;

@Service
public class UserService {

    @Autowired
    private ProfileRepository profileRepository;

    public ResponseEntity<String> updateProfile(Long id, UserUpdateDto userUpdateDto) {

        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            return ResponseEntity.badRequest().body("Unsupported SSO Type");
        }
        profile.setFirstName(userUpdateDto.getFirstName());
        profile.setLastName(userUpdateDto.getLastName());
        profile.setProfileImage(userUpdateDto.getProfileImage());
        profileRepository.save(profile);

        return ResponseEntity.ok("Profile update successfully");
    }

}
