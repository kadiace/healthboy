package com.example.healthboy.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.healthboy.user.dto.CreateUserDto;
import com.example.healthboy.user.dto.UserDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.user.repository.ProfileRepository;
import com.example.healthboy.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Transactional
    public UserDto createOrUpdateUser(CreateUserDto createUserDto) {

        User user = userRepository.findByEmail(createUserDto.getEmail());
        if (user == null) {
            user = new User();
            user.setEmail(createUserDto.getEmail());
        }
        switch (createUserDto.getSsoType()) {
            case GOOGLE:
                user.setGoogleId(createUserDto.getUuid());
                break;
            case FACEBOOK:
                user.setFacebookId(createUserDto.getUuid());
                break;
            case GITHUB:
                user.setGithubId(createUserDto.getUuid());
                break;
            default:
                break;
        }
        user = userRepository.save(user);

        Profile profile = profileRepository.findByUser(user);
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }
        profile.setFirstName(createUserDto.getFirstName());
        profile.setLastName(createUserDto.getLastName());
        profile.setProfileImage(createUserDto.getProfileImage());
        profileRepository.save(profile);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        switch (createUserDto.getSsoType()) {
            case GOOGLE:
                userDto.setGoogleId(createUserDto.getUuid());
                break;
            case FACEBOOK:
                userDto.setFacebookId(createUserDto.getUuid());
                break;
            case GITHUB:
                userDto.setGithubId(createUserDto.getUuid());
                break;
            default:
                break;
        }

        return userDto;
    }
}
