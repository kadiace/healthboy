package com.example.healthboy.user.dto;

import com.example.healthboy.user.entity.Profile;

public class ProfileDto {

    public ProfileDto(String firstName, String lastName, String profileImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImage = profileImage;
    }

    public ProfileDto(Profile profile) {
        this.firstName = profile.getFirstName();
        this.lastName = profile.getLastName();
        this.profileImage = profile.getProfileImage();
    }

    private String firstName;
    private String lastName;
    private String profileImage;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
