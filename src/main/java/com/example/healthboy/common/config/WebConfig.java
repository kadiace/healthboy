package com.example.healthboy.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.healthboy.common.middleware.LoggingInterceptor;
import com.example.healthboy.common.middleware.RouteBasedAuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Autowired
    private RouteBasedAuthInterceptor routeBasedAuthInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(routeBasedAuthInterceptor);
        registry.addInterceptor(loggingInterceptor);
    }

}