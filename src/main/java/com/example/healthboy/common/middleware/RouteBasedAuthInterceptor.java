package com.example.healthboy.common.middleware;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.example.healthboy.common.ApplicationException;
import com.example.healthboy.schedule.entity.ScheduleProfile;
import com.example.healthboy.schedule.service.ScheduleService;
import com.example.healthboy.timeblock.entity.TimeBlock;
import com.example.healthboy.timeblock.service.TimeBlockService;
import com.example.healthboy.user.entity.Profile;
import com.example.healthboy.user.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RouteBasedAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TimeBlockService timeBlockService;

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
        throw new ApplicationException("Check API path guard", HttpStatus.BAD_REQUEST);
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
                throw new ApplicationException("You are not a member of this schedule", HttpStatus.UNAUTHORIZED);
            }
            request.setAttribute("scheduleProfile", scheduleProfile);
            return true;
        }
        throw new ApplicationException("Fail to authorize schedule request", HttpStatus.UNAUTHORIZED);
    }

    private boolean TimeBlockInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String method = request.getMethod();
        Profile profile = ((User) request.getAttribute("user")).getProfile();
        String scheduleUrl = (String) request.getAttribute("url");

        switch (method) {
            case "POST":
                // Check user joined in schedule, save SP in context
                ScheduleProfile scheduleProfile = scheduleService.getScheduleProfile(scheduleUrl, profile);
                if (scheduleProfile == null) {
                    throw new ApplicationException("You are not a member of this schedule", HttpStatus.UNAUTHORIZED);
                }
                request.setAttribute("scheduleProfile", scheduleProfile);
                return true;

            default:
                @SuppressWarnings("unchecked")
                Map<String, String> pathVariables = (Map<String, String>) request
                        .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

                if (pathVariables != null && pathVariables.containsKey("id")) {
                    // Extract id, profile, schedule url
                    String timeBlockId = pathVariables.get("id");

                    // Check user create time block
                    TimeBlock timeBlock = timeBlockService.getTimeBlock(timeBlockId);

                    if (timeBlock.getScheduleProfile().getSchedule().getUrl().equals(scheduleUrl)
                            && timeBlock.getScheduleProfile().getProfile().getId() == profile.getId()) {
                        request.setAttribute("timeBlock", timeBlock);
                        return true;
                    }
                    throw new ApplicationException("You are not a creator of this time block", HttpStatus.UNAUTHORIZED);

                }
                throw new ApplicationException("Fail to authorize time block request", HttpStatus.UNAUTHORIZED);
        }
    }

}