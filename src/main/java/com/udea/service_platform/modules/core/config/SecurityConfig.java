package com.udea.service_platform.modules.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Requerido para probar APIs REST desde curl/Postman
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/register", "/api/roles").permitAll() // Rutas públicas de tu HU
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Documentación
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
