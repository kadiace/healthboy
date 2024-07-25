package com.example.healthboy.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.healthboy.user.dto.UserUpdateDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.user.repository.ProfileRepository;
import com.example.healthboy.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public ResponseEntity<String> updateProfile(Profile profile, UserUpdateDto userUpdateDto) {

        profile.setFirstName(userUpdateDto.getFirstName());
        profile.setLastName(userUpdateDto.getLastName());
        profile.setProfileImage(userUpdateDto.getProfileImage());
        profileRepository.save(profile);

        return ResponseEntity.ok("Profile update successfully");
    }

    public ResponseEntity<String> deleteUser(User user) {
        userRepository.delete(user);
        return ResponseEntity.ok("User delete successfully");
    }

}
