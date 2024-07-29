package com.example.healthboy.auth.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthboy.auth.dto.SignInDto;
import com.example.healthboy.auth.dto.SignUpDto;
import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.common.ApplicationToken;
import com.example.healthboy.common.enums.SSOType;
import com.example.healthboy.common.security.GoogleTokenVerifier;
import com.example.healthboy.user.dto.UserIntegrationDto;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.user.service.UserService;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/auths")
public class AuthController {

    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;

    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {

        String givenToken = signUpDto.getToken();
        SSOType ssoType = signUpDto.getSsoType();

        Profile profile = new Profile();
        User user = new User();

        String email = null;
        String firstName = null;
        String lastName = null;
        String profileImage = null;

        String tokenId = null;
        String applicationToken = null;

        switch (ssoType) {
            case GOOGLE:
                GoogleIdToken.Payload payload = googleTokenVerifier.verifyGoogleToken(givenToken);

                if (payload == null) {
                    throw new ApplicationException("Invalid SSO token", HttpStatus.BAD_REQUEST);
                }

                tokenId = payload.getSubject();
                email = payload.getEmail();
                firstName = (String) payload.get("given_name");
                lastName = (String) payload.get("family_name");
                profileImage = (String) payload.get("picture");
                break;
            default:
                break;
        }

        // Set entity field
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setProfileImage(profileImage);

        user.setEmail(email);
        user.setTokenId(ssoType, tokenId);
        user.setProfile(profile);

        profile.setUser(user);

        // Check SSO token valid
        if (tokenId == null) {
            throw new ApplicationException("Token id not found", HttpStatus.BAD_REQUEST);
        }

        // Check user entity created well
        if (!user.isValid()) {
            throw new ApplicationException("User Info is insufficient", HttpStatus.BAD_REQUEST);
        }

        // Check user already exist
        User existingUser = userService.getUser(user.getEmail());
        if (existingUser != null) {
            throw new ApplicationException("Email already registered", HttpStatus.BAD_REQUEST); // Redirect to SSO
                                                                                                // connect route
        }

        // Save user
        userService.saveUser(user);

        // Create custom bearer token
        applicationToken = ApplicationToken.generateToken(tokenId, ssoType);

        return ResponseEntity.ok(applicationToken);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInDto signInDto) {

        String givenToken = signInDto.getToken();
        SSOType ssoType = signInDto.getSsoType();
        String tokenId = null;

        switch (ssoType) {
            case GOOGLE:
                GoogleIdToken.Payload payload = googleTokenVerifier.verifyGoogleToken(givenToken);

                if (payload == null) {
                    throw new ApplicationException("Invalid SSO token", HttpStatus.BAD_REQUEST);
                }
                tokenId = payload.getSubject();
                break;
            default:
                break;
        }

        if (tokenId == null) {
            throw new ApplicationException("Token id not found", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUser(ssoType, tokenId);

        if (user == null) {
            throw new ApplicationException("User not found", HttpStatus.BAD_REQUEST);
        }

        // Create custom bearer token
        String applicationToken = ApplicationToken.generateToken(tokenId, ssoType);

        return ResponseEntity.ok(applicationToken);
    }

    @Transactional
    @PostMapping("/integration")
    public ResponseEntity<String> postMethodName(HttpServletRequest request,
            @RequestBody UserIntegrationDto userIntegrationDto) throws GeneralSecurityException, IOException {

        User user = (User) request.getAttribute("user");
        String tokenId = null;

        switch (userIntegrationDto.getSsoType()) {
            case GOOGLE:
                // Check already intergreated
                if (user.getGoogleId() != null) {
                    throw new ApplicationException(userIntegrationDto.getSsoType() + " is already integrated",
                            HttpStatus.BAD_REQUEST);
                }

                // Get token payload
                GoogleIdToken.Payload payload = googleTokenVerifier.verifyGoogleToken(userIntegrationDto.getTokenId());
                if (payload == null) {
                    throw new ApplicationException("Invalid SSO token", HttpStatus.BAD_REQUEST);
                }

                // Check same user
                if (!user.getEmail().equals(payload.getEmail())) {
                    throw new ApplicationException("User is not matched", HttpStatus.BAD_REQUEST);
                }

                // Get google SSO id
                tokenId = payload.getSubject();
                break;
            case FACEBOOK:
                if (user.getFacebookId() != null) {
                    throw new ApplicationException(userIntegrationDto.getSsoType() + " is already integrated",
                            HttpStatus.BAD_REQUEST);
                }
                break;
            case GITHUB:
                if (user.getGithubId() != null) {
                    throw new ApplicationException(userIntegrationDto.getSsoType() + " is already integrated",
                            HttpStatus.BAD_REQUEST);
                }
                break;
            default:
                break;
        }

        if (tokenId == null) {
            throw new ApplicationException("Invalid SSO token", HttpStatus.BAD_REQUEST);
        }

        userService.updateUserTokenId(user, userIntegrationDto.getSsoType(), tokenId);
        return ResponseEntity.ok(userIntegrationDto.getSsoType() + " intergration success");
    }

}
