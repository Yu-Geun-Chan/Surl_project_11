package com.koreait.surl_project_11.global.security;

import com.koreait.surl_project_11.global.rsData.RsData;
import com.koreait.surl_project_11.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomAuthenticationFilter customAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST,"/api/*/members/", "/api/*/members/login").permitAll()
                                .requestMatchers("/h2-console/**").permitAll() // permitAll() : 제재하지마
                                .requestMatchers("/actuator/**").permitAll()
                                .anyRequest().authenticated() // 위에 2개가 아니라면 로그인을 검사해
                )
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
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(
                                (request, response, authException) -> {
                                    response.setContentType("application/json; charset=utf-8");
                                    response.setStatus(403);
                                    response.getWriter().write(
                                            Ut.json.toString(
                                                    RsData.of("403-1",request.getRequestURI() + ", " + authException.getLocalizedMessage())
                                            )
                                    );
                                }
                        ))


                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
