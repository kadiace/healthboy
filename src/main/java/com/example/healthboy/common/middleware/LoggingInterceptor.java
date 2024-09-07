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

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        logRequestDetails(request);
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        logResponseDetails(request, response, duration);
    }

    private void logRequestDetails(HttpServletRequest request) {
        String httpMethod = request.getMethod();
        String userAgent = request.getHeader("User-Agent");
        String clientIp = request.getRemoteAddr();
        String requestPath = request.getRequestURI();

        logger.info("Request: [" + httpMethod + "] " + requestPath + " - User-Agent: " + userAgent + " - Client IP: "
                + clientIp);
    }

    private void logResponseDetails(HttpServletRequest request, HttpServletResponse response, long duration) {
        String httpMethod = request.getMethod();
        String requestPath = request.getRequestURI();
        int status = response.getStatus();

        logger.info("Response: [" + httpMethod + "] " + requestPath + " - Status: " + status + " - Time taken: "
                + duration + "ms");
    }
}