package br.com.blogsanapi.infra.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://127.0.0.1:5501")
            .allowedMethods(
                "GET", 
                "POST", 
                "PUT", 
                "DELETE", 
                "OPTIONS", 
                "HEAD", 
                "TRACE", 
                "CONNECT"
            );
    }
}