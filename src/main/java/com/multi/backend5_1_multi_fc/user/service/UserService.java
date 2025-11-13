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
        userDto.setProfile_image(imageUrl); // DTO에 S3 URL 저장

        // 3. 비밀번호 암호화
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // 4. DB 저장
        userDao.insertUser(userDto);
    }

    // 아이디 중복 검사
    public boolean isUsernameTaken(String username) {
        return userDao.countByUsername(username) > 0;
    }

    //이메일 중복 검사
    public boolean isEmailTaken(String email) {
        return userDao.countByEmail(email) > 0;
    }

    //닉네임 중복 검사
    public boolean isNicknameTaken(String nickname) {
        return userDao.countByNickname(nickname) > 0;
    }
}