package com.multi.backend5_1_multi_fc.mypage.service;

import com.multi.backend5_1_multi_fc.mypage.dto.MyPageDto;
import com.multi.backend5_1_multi_fc.mypage.exception.MyPageException;
import com.multi.backend5_1_multi_fc.mypage.repository.MyPageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageRepo myPageRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * username으로 내 정보 조회
     * (username -> User row -> userId 포함된 DTO 반환)
     */
    public MyPageDto getMyInfo(String username) {
        MyPageDto dto = myPageRepo.findByUsername(username);
        if (dto == null) {
            throw new MyPageException("존재하지 않는 사용자입니다.");
        }
        return dto;
    }

    /**
     * username으로 userId 조회 (내부용)
     */
    private Long getUserIdByUsername(String username) {
        MyPageDto dto = myPageRepo.findByUsername(username);
        if (dto == null || dto.getUserId() == null) {
            throw new MyPageException("사용자 정보를 찾을 수 없습니다.");
        }
        return dto.getUserId();
    }

    /**
     * 개인정보 수정 전 비밀번호 확인 (userId 기반)
     */
    public void verifyPassword(String username, MyPageDto.PasswordVerifyRequest request) {
        Long userId = getUserIdByUsername(username);
        String encodedPassword = myPageRepo.findPasswordByUserId(userId);

        if (encodedPassword == null ||
                !passwordEncoder.matches(request.getCurrentPassword(), encodedPassword)) {
            throw new MyPageException("비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 개인정보 수정 (userId 기준 UPDATE)
     */
    @Transactional
    public void updateProfile(String username, MyPageDto.UpdateProfileRequest request) {
        Long userId = getUserIdByUsername(username);
        myPageRepo.updateProfileByUserId(userId, request);
    }

    /**
     * 비밀번호 변경 (userId 기준 UPDATE)
     */
    @Transactional
    public void updatePassword(String username, MyPageDto.UpdatePasswordRequest request) {
        Long userId = getUserIdByUsername(username);
        String encodedPassword = myPageRepo.findPasswordByUserId(userId);

        if (encodedPassword == null ||
                !passwordEncoder.matches(request.getCurrentPassword(), encodedPassword)) {
            throw new MyPageException("현재 비밀번호가 일치하지 않습니다.");
        }

        String newEncoded = passwordEncoder.encode(request.getNewPassword());
        myPageRepo.updatePasswordByUserId(userId, newEncoded);
    }
}
