package com.multi.backend5_1_multi_fc.security;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT username, password FROM User WHERE username = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
            }

            String dbUsername = rs.getString("username");
            String dbPassword = rs.getString("password");
            if (dbPassword != null) {
                dbPassword = dbPassword.trim(); // CHAR 공백 방지
            }

            System.out.println("AUTH DEBUG => " + dbUsername + " / " + dbPassword);

            return User.withUsername(dbUsername)
                    .password(dbPassword) // BCrypt 해시 들어감
                    .roles("USER")
                    .build();
        }, username);
    }
}
