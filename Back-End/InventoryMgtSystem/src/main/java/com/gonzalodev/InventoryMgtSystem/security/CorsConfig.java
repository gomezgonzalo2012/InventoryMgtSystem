package com.gonzalodev.InventoryMgtSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    // custom configuration for Spring MVC cors
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
       return new WebMvcConfigurer() {
           @Override
           public void addCorsMappings(CorsRegistry registry) {
               registry.addMapping("/**") // aply to all routes
                       .allowedMethods("GET", "POST", "PUT", "DELETE")
                       .allowedOrigins("*"); // only for teste purposes
           }
       };
    }
}
