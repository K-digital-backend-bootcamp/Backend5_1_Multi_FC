package com.multi.backend5_1_multi_fc.user.dao;

import com.multi.backend5_1_multi_fc.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException; // [추가]
import org.springframework.jdbc.core.BeanPropertyRowMapper; // [추가]
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

    // 로그인 기능용
    // UserService의 login 메서드가 호출하는 메서드
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

    // 1. (아이디 찾기용) 이메일 주소로 사용자 아이디(username)를 찾음
    public String findUsernameByEmail(String email) {
        String sql = "SELECT username FROM User WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, email);
        } catch (EmptyResultDataAccessException e) {
            // 일치하는 이메일이 없으면 null 반환
            return null;
        }
    }

    // 2. (비밀번호 찾기용) 아이디와 이메일이 모두 일치하는 사용자가 있는지 확인
    public boolean checkUserByUsernameAndEmail(String username, String email) {
        String sql = "SELECT COUNT(*) FROM User WHERE username = ? AND email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, email);
        return count != null && count > 0;
    }

    // 3. (비밀번호 찾기용) 인증번호와 만료시간을 DB에 저장
    public void updateResetCode(String email, String code) {
        // 인증코드를 저장하고, 만료 시간을 '현재시간 + 5분'으로 설정
        String sql = "UPDATE User SET reset_code = ?, reset_code_expires = DATE_ADD(NOW(), INTERVAL 5 MINUTE) WHERE email = ?";
        jdbcTemplate.update(sql, code, email);
    }

    // 4. (비밀번호 찾기용) 이메일과 코드로 사용자 검증
    public boolean verifyResetCode(String email, String code) {
        // 이메일과 코드가 일치하고, 만료시간이 지나지 않았는지 확인
        String sql = "SELECT COUNT(*) FROM User WHERE email = ? AND reset_code = ? AND reset_code_expires > NOW()";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email, code);
        return count != null && count > 0;
    }

    // 5. (비밀번호 찾기용) 새 비밀번호로 업데이트
    public void updatePasswordByEmail(String email, String newEncryptedPassword) {
        // 인증이 완료되었으므로 코드와 만료시간은 초기화(NULL)
        String sql = "UPDATE User SET password = ?, reset_code = NULL, reset_code_expires = NULL WHERE email = ?";
        jdbcTemplate.update(sql, newEncryptedPassword, email);
    }


    // userId로 사용자 정보 조회
    public UserDto findByUserId(Long userId) {
        String sql = "SELECT * FROM User WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDto.class), userId);
        } catch (EmptyResultDataAccessException e) {
            // 일치하는 유저가 없으면 null을 반환합니다.
            return null;
        }
    }
    // 닉네임으로 사용자 검색 (부분 일치)
    public List<UserDto> findUsersByNickname(String nickname) {
        String sql = "SELECT * FROM User WHERE nickname LIKE ? LIMIT 10";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), "%" + nickname + "%");
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }



}