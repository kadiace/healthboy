package com.example.healthboy.common.dto;

import com.example.healthboy.common.enums.SSOType;

public class TokenInfo {
    private String tokenId;
    private SSOType ssoType;

    public TokenInfo(String tokenId, SSOType ssoType) {
        this.tokenId = tokenId;
        this.ssoType = ssoType;
    }

    // Getters and Setters
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
