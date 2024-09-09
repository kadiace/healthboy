package com.example.healthboy.auth.dto;

import com.example.healthboy.common.enums.SSOType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignUpDto {

    @NotNull(message = "SSO type must not be null")
    private SSOType ssoType;

    @NotBlank(message = "Token id must not be null")
    private String token;

    public SSOType getSsoType() {
        return ssoType;
    }

    public String getToken() {
        return token;
    }
}
