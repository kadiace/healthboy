package com.example.healthboy.common.security;

import org.springframework.stereotype.Service;

@Service
public class FacebookTokenVerifier {

    public boolean verifyFacebookToken(String accessToken) {
        return false;
    }
}
