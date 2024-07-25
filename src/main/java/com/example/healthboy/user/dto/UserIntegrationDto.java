package com.example.healthboy.user.dto;

import com.example.healthboy.user.enums.SSOType;

public class UserIntegrationDto {
    private SSOType ssoType;
    private String tokenId;

    // Getters and setters
    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public SSOType getSsoType() {
        return ssoType;
    }

    public void setSsoType(SSOType ssoType) {
        this.ssoType = ssoType;
    }
}
