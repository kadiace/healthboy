package com.example.healthboy.auth.dto;

import com.example.healthboy.common.enums.SSOType;

import jakarta.validation.constraints.NotBlank;

public class IntegrationDto {

    @NotBlank(message = "SSO type must not be null")
    private SSOType ssoType;

    @NotBlank(message = "Token id must not be null")
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
