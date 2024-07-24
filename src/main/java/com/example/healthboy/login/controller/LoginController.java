package com.example.healthboy.login.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.healthboy.common.Util;
import org.springframework.web.bind.annotation.*;

import com.example.healthboy.common.security.GoogleTokenVerifier;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.user.enums.SSOType;
import com.example.healthboy.user.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/sign-in")
    public ResponseEntity<String> singIn(HttpServletRequest request) throws GeneralSecurityException, IOException {
        String authHeader = request.getHeader("Authorization");
        String rawSsoType = request.getHeader("SSO-Type");
        SSOType ssoType = Util.safeValueOf(SSOType.class, rawSsoType);
        String token = authHeader.substring(7);

        Profile profile = new Profile();
        User user = new User();

        switch (ssoType) {
            case GOOGLE:
                GoogleIdToken.Payload payload = googleTokenVerifier.verifyGoogleToken(token);
                if (payload != null) {
                    String googleId = payload.getSubject();
                    String email = payload.getEmail();
                    String firstName = (String) payload.get("given_name");
                    String lastName = (String) payload.get("family_name");
                    String profileImage = (String) payload.get("picture");

                    profile.setFirstName(firstName);
                    profile.setLastName(lastName);
                    profile.setProfileImage(profileImage);

                    user.setEmail(email);
                    user.setGoogleId(googleId);
                    user.setProfile(profile);

                    profile.setUser(user);
                }
                break;
            default:
                break;
        }

        if (user.isValid()) {
            User existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null) {
                return ResponseEntity.badRequest().body("Email already registered"); // Redirect to SSO connect route
            } else {
                userRepository.save(user);
                return ResponseEntity.ok("User and profile created successfully");
            }
        }

        return ResponseEntity.badRequest().body("Invalid user or profile data");
    }
}
