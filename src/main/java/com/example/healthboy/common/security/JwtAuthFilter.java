package com.example.healthboy.common.security;

import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.common.ApplicationToken;
import com.example.healthboy.common.dto.TokenInfo;
import com.example.healthboy.common.enums.SSOType;
import com.example.healthboy.user.entity.User;
import com.example.healthboy.user.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        return requestUri.startsWith("/api/health") ||
                requestUri.startsWith("/api/auths/sign-up")
                || requestUri.startsWith("/api/auths/sign-in");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        try {
            // Decode token and retrieve user details
            TokenInfo tokenInfo = ApplicationToken.decodeToken(token);
            String tokenId = tokenInfo.getTokenId();
            SSOType ssoType = tokenInfo.getSsoType();

            // Fetch user by tokenId and ssoType
            User user = userService.getUser(ssoType, tokenId);
            if (user == null) {
                throw new ApplicationException("User not found for the provided token.", HttpStatus.FORBIDDEN);
            }
            // Set user details into SecurityContext for authentication
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                    null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Set user attribute for further usage
            request.setAttribute("user", user);
            filterChain.doFilter(request, response);
            return;
        } catch (ApplicationException e) {
            response.setStatus(e.getStatus().value());
            response.getWriter().write(e.getMessage());
            return;
        }
    }
}
