package com.example.healthboy.common.middleware;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestUri = request.getRequestURI();

        if (isPublicPath(requestUri)) {
            return true;
        } else if (requestUri.startsWith("/api/schedules")) {
            return ScheduleInterceptor(request, response, handler);
        } else if (requestUri.startsWith("/api/time-blocks")) {
            return TimeBlockInterceptor(request, response, handler);
        }
        throw new ApplicationException("Invalid API path", HttpStatus.BAD_REQUEST);
    }

    private boolean isPublicPath(String requestUri) {
        return requestUri.startsWith("/api/health") || requestUri.startsWith("/api/auths")
                || requestUri.startsWith("/api/users");
    }

    private boolean ScheduleInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // Pass guard when POST method.
        if (HttpMethod.POST.matches(request.getMethod())) {
            return true;
        }

        // Check if the handler is a method and extract the URL path variable
        Map<String, String> pathVariables = getPathVariables(request);

        if (!pathVariables.containsKey("url")) {
            throw new ApplicationException("Access denied: cannot find path parmater 'url'", HttpStatus.UNAUTHORIZED);
        }

        // Extract url, profile
        String scheduleUrl = pathVariables.get("url");
        Profile profile = getUserProfile(request);

        // Check user joined in schedule, save SP in context
        checkScheduleMembership(scheduleUrl, profile, request);
        return true;
    }

    private boolean TimeBlockInterceptor(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String scheduleUrl = request.getHeader("Schedule-Url");
        Profile profile = getUserProfile(request);

        if (scheduleUrl == null) {
            throw new ApplicationException("Access denied: cannot find header 'Schedule-Url'", HttpStatus.UNAUTHORIZED);
        }

        if (HttpMethod.POST.matches(request.getMethod())) {
            checkScheduleMembership(scheduleUrl, profile, request);
            return true;
        }

        // Check if the handler is a method and extract the URL path variable
        Map<String, String> pathVariables = getPathVariables(request);

        if (!pathVariables.containsKey("id")) {
            throw new ApplicationException("Access denied: cannot find path parmater 'id'",
                    HttpStatus.UNAUTHORIZED);
        }
        // Extract id, profile, schedule url
        String timeBlockId = pathVariables.get("id");

        // Check user create time block
        TimeBlock timeBlock = getTimeBlock(timeBlockId);
        if (!isOwnerOfTimeBlock(timeBlock, scheduleUrl, profile)) {
            throw new ApplicationException("Access denied: not the owner of this time block",
                    HttpStatus.UNAUTHORIZED);
        }

        request.setAttribute("timeBlock", timeBlock);
        return true;

    }

    private Map<String, String> getPathVariables(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Map<String, String> pathVariables = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVariables == null) {
            throw new ApplicationException("Access denied: cannot find any path parmaters", HttpStatus.UNAUTHORIZED);
        }
        return pathVariables;
    }

    private Profile getUserProfile(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        if (user == null) {
            throw new ApplicationException("Route Interceptor: User not found in request", HttpStatus.UNAUTHORIZED);
        }
        return user.getProfile();
    }

    private void checkScheduleMembership(String scheduleUrl, Profile profile, HttpServletRequest request) {
        ScheduleProfile scheduleProfile = scheduleService.getScheduleProfile(scheduleUrl, profile);
        if (scheduleProfile == null) {
            throw new ApplicationException("Access denied: not a member of this schedule", HttpStatus.UNAUTHORIZED);
        }

        request.setAttribute("scheduleProfile", scheduleProfile);
    }

    private TimeBlock getTimeBlock(String timeBlockId) {
        TimeBlock timeBlock = timeBlockService.getTimeBlock(timeBlockId);
        if (timeBlock == null) {
            throw new ApplicationException("Access denied: Invalid time block id",
                    HttpStatus.UNAUTHORIZED);
        }
        return timeBlock;
    }

    private boolean isOwnerOfTimeBlock(TimeBlock timeBlock, String scheduleUrl, Profile profile) {
        return timeBlock.getScheduleProfile().getSchedule().getUrl().equals(scheduleUrl)
                && timeBlock.getScheduleProfile().getProfile().getId().equals(profile.getId());
    }

}