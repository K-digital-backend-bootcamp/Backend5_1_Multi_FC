package com.multi.backend5_1_multi_fc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF(Cross-Site Request Forgery) POST/PUT 요청이 차단
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Spring Security가 기본으로 제공하는 로그인 폼을 비활성화
                .formLogin(AbstractHttpConfigurer::disable)

                // 3. HTTP Basic 인증(브라우저 팝업 인증)을 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)

                // 4. 모든 HTTP 요청에 대해 인증 없이 접근을 허용
                // 주의 (개발 완료 후에는 .requestMatchers("/api/...).permitAll()" 등으로 변경 해야 함)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}