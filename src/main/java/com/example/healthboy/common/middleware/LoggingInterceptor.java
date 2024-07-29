package com.example.healthboy.common.middleware;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(LoggingInterceptor.class.getName());

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {
        logRequestDetails(request);
        return true;
    }

    private void logRequestDetails(HttpServletRequest request) {
        String httpMethod = request.getMethod();
        String userAgent = request.getHeader("User-Agent");
        String clientIp = request.getRemoteAddr();
        String requestPath = request.getRequestURI();

        logger.info("[" + httpMethod + "] " + requestPath + " - " + userAgent + " " + clientIp);
    }
}