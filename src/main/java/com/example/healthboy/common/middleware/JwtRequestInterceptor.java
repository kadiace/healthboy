package com.example.healthboy.common.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.common.ApplicationToken;
import com.example.healthboy.common.dto.TokenInfo;
import com.example.healthboy.common.enums.SSOType;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // Pass guard when path is /api/auths/sign-up
        String requestUri = request.getRequestURI();
        if (requestUri.equals("/api/health") || requestUri.equals("/api/auths/sign-up")
                || requestUri.equals("/api/auths/sign-in")) {
            return true;
        }

        // Verify
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            TokenInfo tokenInfo = ApplicationToken.decodeToken(token);

            String tokenId = tokenInfo.getTokenId();
            SSOType ssoType = tokenInfo.getSsoType();

            User user = userService.getUser(ssoType, tokenId);

            if (user != null) {
                request.setAttribute("user", user);
                return true;
            }
        }

        throw new ApplicationException("Check token is valid", HttpStatus.UNAUTHORIZED);
    }
}
