package com.example.healthboy.common.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.common.Util;
import com.example.healthboy.common.security.GoogleTokenVerifier;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.user.enums.SSOType;
import com.example.healthboy.user.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestInterceptor implements HandlerInterceptor {

    @Autowired
    private GoogleTokenVerifier googleTokenVerifier;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // Pass guard when path is /api/auths/sign-up
        String requestUri = request.getRequestURI();
        if (requestUri.equals("/api/auths/sign-up")) {
            return true;
        }

        // Verify
        String authHeader = request.getHeader("Authorization");
        String rawSsoType = request.getHeader("SSO-Type");
        SSOType ssoType = Util.safeValueOf(SSOType.class, rawSsoType);

        if (authHeader != null && authHeader.startsWith("Bearer ") && ssoType != null) {
            String token = authHeader.substring(7);
            User user = null;

            switch (ssoType) {
                case GOOGLE:
                    GoogleIdToken.Payload payload = googleTokenVerifier.verifyGoogleToken(token);
                    if (payload != null) {
                        String googleId = payload.getSubject();
                        user = userRepository.findByGoogleId(googleId);
                    }
                    break;
                default:
                    break;
            }

            if (user != null) {
                request.setAttribute("user", user);
                return true;
            }
        }

        throw new ApplicationException("Check token is valid", HttpStatus.UNAUTHORIZED);
    }
}
