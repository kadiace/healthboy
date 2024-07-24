package com.example.healthboy.common.security;

import org.springframework.stereotype.Service;

@Service
public class GithubTokenVerifier {

    public boolean verifyGitHubToken(String accessToken) {
        return false;
    }
}
