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
                user.getProfileImage()
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

    // user_id로 사용자 조회
    public UserDto findByUserId(Long userId) {
        String sql = "SELECT * FROM User WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            UserDto user = new UserDto();
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setNickname(rs.getString("nickname"));
            user.setProfileImage(rs.getString("profile_image"));
            user.setAddress(rs.getString("address"));
            user.setEmail(rs.getString("email"));
            user.setLevel(rs.getString("level"));
            user.setPosition(rs.getString("position"));
            user.setGender(rs.getString("gender"));
            user.setLoginFailCount(rs.getInt("login_fail_count"));
            user.setLockedUntil(rs.getTimestamp("locked_until"));
            user.setCreatedAt(rs.getTimestamp("created_at"));
            user.setUpdatedAt(rs.getTimestamp("updated_at"));
            user.setResetCode(rs.getString("reset_code"));
            user.setResetCodeExpires(rs.getTimestamp("reset_code_expires"));
            user.setLastCheckedCommentId(rs.getLong("last_checked_comment_id"));
            return user;
        }, userId);
    }

    // username으로 사용자 조회
    public UserDto findByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            UserDto user = new UserDto();
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setNickname(rs.getString("nickname"));
            user.setProfileImage(rs.getString("profile_image"));
            user.setAddress(rs.getString("address"));
            user.setEmail(rs.getString("email"));
            user.setLevel(rs.getString("level"));
            user.setPosition(rs.getString("position"));
            user.setGender(rs.getString("gender"));
            user.setLoginFailCount(rs.getInt("login_fail_count"));
            user.setLockedUntil(rs.getTimestamp("locked_until"));
            user.setCreatedAt(rs.getTimestamp("created_at"));
            user.setUpdatedAt(rs.getTimestamp("updated_at"));
            user.setResetCode(rs.getString("reset_code"));
            user.setResetCodeExpires(rs.getTimestamp("reset_code_expires"));
            user.setLastCheckedCommentId(rs.getLong("last_checked_comment_id"));
            return user;
        }, username);
    }
}