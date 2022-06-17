package com.jungeunhong.datajpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // welcome page 세팅
        registry.addRedirectViewController("/","/home");
        registry.addViewController("/home").setViewName("home");
    }
}
