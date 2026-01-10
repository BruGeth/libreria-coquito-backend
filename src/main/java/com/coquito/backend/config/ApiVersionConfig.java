package com.coquito.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.method.HandlerTypePredicate;

@Configuration
public class ApiVersionConfig implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        // Estrategia: /api/v1/...
        configurer.usePathSegment(1);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
                "/api/v{version}",
                HandlerTypePredicate.forAnnotation(RestController.class));
    }
}
