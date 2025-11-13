package com.multi.backend5_1_multi_fc.user.dao;

import com.multi.backend5_1_multi_fc.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    // 회원가입 (INSERT)
    public void insertUser(UserDto user) {

        String sql = "INSERT INTO User (username, password, nickname, email, level, position, gender, address, profile_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(), // 암호화된 비밀번호
                user.getNickname(),
                user.getEmail(),
                user.getLevel(),
                user.getPosition(),
                user.getGender(),
                user.getAddress(),
                user.getProfile_image()
        );
    }

    // 아이디 중복 체크
    public int countByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM User WHERE username = ?";
        // 결과가 0 또는 1로 나옴
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }

    // 이메일 중복 체크
    public int countByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM User WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, email);
    }

    // 닉네임 중복 체크
    public int countByNickname(String nickname) {
        String sql = "SELECT COUNT(*) FROM User WHERE nickname = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, nickname);
    }
}