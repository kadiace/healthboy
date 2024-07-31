package com.example.healthboy.user.dto;

import jakarta.validation.constraints.NotBlank;

public class ProfileUpdateDto {

    @NotBlank(message = "First name must not be null")
    private String firstName;

    @NotBlank(message = "Last name must not be null")
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
