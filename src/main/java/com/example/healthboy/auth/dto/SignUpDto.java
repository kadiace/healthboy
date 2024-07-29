package com.example.healthboy.auth.dto;

import com.example.healthboy.common.enums.SSOType;

public class SignUpDto {

    private SSOType ssoType;
    private String token;

    public SSOType getSsoType() {
        return ssoType;
    }

    public String getToken() {
        return token;
    }
}
