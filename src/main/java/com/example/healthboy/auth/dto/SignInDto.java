package com.example.healthboy.auth.dto;

import com.example.healthboy.common.enums.SSOType;

public class SignInDto {

    private SSOType ssoType;
    private String token;

    public SSOType getSsoType() {
        return ssoType;
    }

    public String getToken() {
        return token;
    }

}
