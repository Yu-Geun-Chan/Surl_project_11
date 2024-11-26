package com.koreait.surl_project_11.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/h2-console/**").permitAll() // permitAll() : 제재하지마
                                .requestMatchers("/actuator/**").permitAll()
                                .anyRequest().authenticated()
                )
                // h2-console을 위한
                .headers(
                        headers ->
                                headers.frameOptions(
                                        frameOptions ->
                                                frameOptions.sameOrigin()
                                )
                )
                .csrf(
                        csrf ->
                                csrf.disable()
                ) // 타임리프, MPA에서는 csrf를 사용한다. 하지만 REST API 방식은 csrf를 끈다.
                .formLogin(formLogin ->
                        formLogin.permitAll()
                );

        return http.build();
    }
}
