package com.coquito.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Desactivar CSRF (API REST, no formularios)
            .csrf(csrf -> csrf.disable())

            // Permitir todas las solicitudes por ahora
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )

            // No usar sesiones (preparado para JWT más adelante)
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    org.springframework.security.config.http.SessionCreationPolicy.STATELESS
                )
            );

        return http.build();
    }
}