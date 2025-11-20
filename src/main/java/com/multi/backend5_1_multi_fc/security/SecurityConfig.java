package com.multi.backend5_1_multi_fc.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        /*  =============================
                         *   1) 인증 없이 허용되는 API
                         *  ============================= */

                        // User API permitAll
                        .requestMatchers("/api/users/login", "/api/users/signup").permitAll()
                        .requestMatchers("/api/users/check-username", "/api/users/check-email", "/api/users/check-nickname").permitAll()
                        .requestMatchers("/api/users/find-id", "/api/users/reset-password/**").permitAll()

                        // match 모듈에서 허용해야 하는 API들
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()

                        /* =============================
                         *   2) 정적 리소스
                         * ============================= */
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.ico").permitAll()

                        /* =============================
                         *   3) HTML 페이지 허용
                         * ============================= */
                        .requestMatchers(
                                "/", "/login", "/register", "/forgot-password",
                                "/mypage", "/profile/edit", "/friends", "/chat",
                                "/fields", "/stadium/detail",
                                "/schedule", "/schedule/add", "/schedule/detail/**", "/schedule/private/detail",
                                "/community", "/community/write", "/community/detail/**",
                                "/reviews/write",
                                "/team/create", "/team/manage", "/team/invite", "/team-edit"
                        ).permitAll()

                        /* =============================
                         *   4) 나머지는 인증 필요
                         * ============================= */
                        .anyRequest().authenticated()
                )

                /* JWT 필터 적용 */
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
