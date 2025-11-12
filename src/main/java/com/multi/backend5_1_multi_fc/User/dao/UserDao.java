package com.multi.backend5_1_multi_fc.User.dao;

import com.multi.backend5_1_multi_fc.User.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException; // [추가]
import org.springframework.jdbc.core.BeanPropertyRowMapper; // [추가]
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// import java.util.List; // (Moon님 코드에 없으므로 삭제)

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
                user.getProfileImage() // [수정] user.getProfile_image() -> user.getProfileImage()
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

    // --- [로그인 기능용 추가] ---
    // UserService의 login 메서드가 호출하는 핵심 메서드입니다.
    public UserDto findUserByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try {
            // queryForObject는 결과가 1개일 때 사용합니다.
            // BeanPropertyRowMapper가 DB의 snake_case(user_id)를 DTO의 camelCase(userId)로 자동 변환해줍니다.
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDto.class), username);
        } catch (EmptyResultDataAccessException e) {
            // 일치하는 유저가 없으면 null을 반환합니다.
            return null;
        }
    }

    // (findUsersByIds 메서드는 Moon님 코드에 없으므로 제외)
}