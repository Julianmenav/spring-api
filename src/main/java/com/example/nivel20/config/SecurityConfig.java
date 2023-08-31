package com.example.nivel20.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests( auth -> {
                auth.requestMatchers("/","/main.css", "/pruebita").permitAll();
                auth.anyRequest().authenticated();
            })
            .oauth2Login(customizer ->
                    customizer.defaultSuccessUrl("/", true)
            )
            .csrf((csrf) -> csrf.disable());
        return http.build();
    }

}