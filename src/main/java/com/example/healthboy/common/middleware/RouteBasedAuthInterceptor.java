package com.example.healthboy.common.middleware;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.schedule.service.ScheduleService;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RouteBasedAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestUri = request.getRequestURI();

        if (requestUri.startsWith("/api/users")) {
            return true;
        } else if (requestUri.startsWith("/api/schedules")) {
            return ScheduleInterceptor(request, response, handler);
        } else if (requestUri.startsWith("/api/time-blocks")) {
            return TimeBlockInterceptor(request, response, handler);
        }
        response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        return false;
    }

    private boolean ScheduleInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String method = request.getMethod();

        // Pass guard when POST method.
        if (method.equals("POST")) {
            return true;
        }

        // Check if the handler is a method and extract the URL path variable
        @SuppressWarnings("unchecked")
        Map<String, String> pathVariables = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (pathVariables != null && pathVariables.containsKey("url")) {
            // Extract url, profile
            String scheduleUrl = pathVariables.get("url");
            Profile profile = ((User) request.getAttribute("user")).getProfile();

            // Check user joined in schedule, save SP in context
            ScheduleProfile scheduleProfile = scheduleService.getScheduleProfile(scheduleUrl, profile);
            if (scheduleProfile == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            request.setAttribute("scheduleProfile", scheduleProfile);
            return true;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    private boolean TimeBlockInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String method = request.getMethod();

        switch (method) {
            case "POST":
                // Extract url, profile
                String scheduleUrl = (String) request.getAttribute("url");
                Profile profile = ((User) request.getAttribute("user")).getProfile();

                // Check user joined in schedule, save SP in context
                ScheduleProfile scheduleProfile = scheduleService.getScheduleProfile(scheduleUrl, profile);
                if (scheduleProfile == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
                request.setAttribute("scheduleProfile", scheduleProfile);
                return true;

            default:
                @SuppressWarnings("unchecked")
                Map<String, String> pathVariables = (Map<String, String>) request
                        .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

                if (pathVariables != null && pathVariables.containsKey("id")) {
                    String timeBlockId = pathVariables.get("id");
                    System.out.println("Extracted URL variable: " + timeBlockId);
                    // Check user create time block

                    return true;
                }
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
        }
    }

}