package com.multi.backend5_1_multi_fc.user.service;

import com.multi.backend5_1_multi_fc.user.dao.UserDao;
import com.multi.backend5_1_multi_fc.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;

    @Transactional
    public void signup(UserDto userDto, MultipartFile profileImage) throws IOException {
        // 1. 중복 체크 (signup 자체 로직)
        if (userDao.countByUsername(userDto.getUsername()) > 0) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
        if (userDao.countByEmail(userDto.getEmail()) > 0) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
        if (userDao.countByNickname(userDto.getNickname()) > 0) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }

        // 2. S3에 파일 업로드
        String imageUrl = s3Service.uploadFile(profileImage);

        // --- [핵심 수정] ---
        // userDto.setProfile_image(imageUrl); -> userDto.setProfileImage(imageUrl);
        userDto.setProfileImage(imageUrl); // DTO의 profile_image 필드에 S3 URL 저장

        // 3. 비밀번호 암호화
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // 4. DB 저장
        userDao.insertUser(userDto);
    }

    // [추가] 로그인 (비밀번호 비교)
    public UserDto login(String username, String rawPassword) {
        // 1. 아이디로 DB에서 유저 정보 조회 (암호화된 비번 포함)
        UserDto user = userDao.findUserByUsername(username);

        // 2. 유저가 존재하고, 입력된 비밀번호(raw)와 DB의 암호화된 비밀번호(encoded)가 일치하는지 확인
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            // (TODO: 로그인 성공 시 login_fail_count 0으로 리셋)
            return user; // 로그인 성공
        }

        // (TODO: 로그인 실패 시 login_fail_count 1 증가)
        return null; // 로그인 실패
    }

    // --- 실시간 중복 확인 API용 메서드 ---

    // 아이디 중복 검사
    public boolean isUsernameTaken(String username) {
        return userDao.countByUsername(username) > 0;
    }

    // 이메일 중복 검사
    public boolean isEmailTaken(String email) {
        return userDao.countByEmail(email) > 0;
    }

    // 닉네임 중복 검사
    public boolean isNicknameTaken(String nickname) {
        return userDao.countByNickname(nickname) > 0;
    }

    public UserDto getUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

}