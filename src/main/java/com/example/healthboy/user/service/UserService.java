package com.example.healthboy.user.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.common.enums.SSOType;
import com.example.healthboy.user.dto.ProfileUpdateDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Timeblock not found", HttpStatus.BAD_REQUEST));
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUser(SSOType ssoType, String tokenId) {
        switch (ssoType) {
            case GOOGLE:
                return userRepository.findByGoogleId(tokenId);
            case FACEBOOK:
                return userRepository.findByFacebookId(tokenId);
            case GITHUB:
                return userRepository.findByGithubId(tokenId);
        }
        throw new ApplicationException("SSO Type is invalid", HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public User updateUserTokenId(User user, SSOType ssoType, String tokenId) {
        user.setTokenId(ssoType, tokenId);
        return user;
    }

    @Transactional
    public Profile updateProfile(Profile profile, ProfileUpdateDto userUpdateDto) {
        profile.setFirstName(userUpdateDto.getFirstName());
        profile.setLastName(userUpdateDto.getLastName());
        profile.setProfileImage(userUpdateDto.getProfileImage());
        return profile;
    }

    @Transactional
    public void deleteUser(User user) {
        user.setDeletedAt(LocalDateTime.now());
    }

    @Transactional
    public void deleteProfile(Profile profile) {
        profile.setDeletedAt(LocalDateTime.now());
    }

}
