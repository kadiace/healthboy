package com.example.healthboy.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.healthboy.common.middleware.LoggingInterceptor;
import com.example.healthboy.common.middleware.RouteBasedAuthInterceptor;
import com.example.healthboy.common.middleware.JwtRequestInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Autowired
    private JwtRequestInterceptor jwtRequestInterceptor;

    @Autowired
    private RouteBasedAuthInterceptor routeBasedAuthInterceptor;

    @Autowired
    private OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor);
        registry.addInterceptor(loggingInterceptor);
        registry.addInterceptor(jwtRequestInterceptor);
        registry.addInterceptor(routeBasedAuthInterceptor);
    }

}