package com.multi.backend5_1_multi_fc.mypage.controller;

import com.multi.backend5_1_multi_fc.mypage.dto.MyPageDto;
import com.multi.backend5_1_multi_fc.mypage.exception.MyPageException;
import com.multi.backend5_1_multi_fc.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<MyPageDto> getMyInfo(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();
        MyPageDto myInfo = myPageService.getMyInfo(username);
        return ResponseEntity.ok(myInfo);
    }

    // 개인정보 수정 전 비밀번호 확인
    @PostMapping("/verify-password")
    public ResponseEntity<Void> verifyPassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody MyPageDto.PasswordVerifyRequest request
    ) {
        String username = userDetails.getUsername();
        myPageService.verifyPassword(username, request);
        return ResponseEntity.ok().build();
    }

    // 개인정보 수정
    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody MyPageDto.UpdateProfileRequest request
    ) {
        String username = userDetails.getUsername();
        myPageService.updateProfile(username, request);
        return ResponseEntity.ok().build();
    }

    // 비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody MyPageDto.UpdatePasswordRequest request
    ) {
        String username = userDetails.getUsername();
        myPageService.updatePassword(username, request);
        return ResponseEntity.ok().build();
    }

    // 마이페이지 예외 처리
    @ExceptionHandler(MyPageException.class)
    public ResponseEntity<String> handleMyPageException(MyPageException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
