package com.example.healthboy.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.repository.ProfileRepository;

@Service
public class UserService {

    @Autowired
    private ProfileRepository profileRepository;

    public ResponseEntity<String> updateProfile(Long id, String temp) {

        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            return ResponseEntity.badRequest().body("Unsupported SSO Type");
        }
        profileRepository.save(profile);

        return ResponseEntity.ok("Profile update successfully");
    }

}
