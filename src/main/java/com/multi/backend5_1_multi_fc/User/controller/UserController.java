package com.multi.backend5_1_multi_fc.User.controller;

import com.multi.backend5_1_multi_fc.User.dto.UserDto;
import com.multi.backend5_1_multi_fc.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map; // [추가] 로그인 요청을 받기 위해 import
import java.util.HashMap; // [추가] 로그인 응답을 주기 위해 import

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // [기존] 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @ModelAttribute UserDto userDto,
            @RequestParam(value = "profile_image_file", required = false) MultipartFile profileImageFile
    ) {
        System.out.println("👉 [요청 도착] DTO: " + userDto);
        System.out.println("👉 [요청 도착] 파일: " + (profileImageFile != null ? profileImageFile.getOriginalFilename() : "없음"));

        try {
            userService.signup(userDto, profileImageFile);
            return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("서버 에러 발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- [로그인 기능 추가] ---
    // login.html의 스크립트가 호출하는 API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String rawPassword = payload.get("password");

        try {
            // 1. 서비스로 아이디/비번을 보내 인증 요청
            UserDto user = userService.login(username, rawPassword);

            if (user != null) {
                // 2. 로그인 성공
                // (보안) DTO에서 민감 정보(비밀번호 등) 제거
                user.setPassword(null);
                user.setResetCode(null);
                user.setResetCodeExpires(null);

                // (임시) JWT 토큰 생성 (추후 실제 JWT 라이브러리로 교체)
                // login.html 스크립트가 'accessToken'을 기대하고 있습니다.
                String dummyToken = "dummy-jwt-token-for-" + user.getUsername();

                // 3. 프론트엔드로 토큰과 사용자 정보 반환
                Map<String, Object> response = new HashMap<>();
                response.put("accessToken", dummyToken);
                response.put("user", user); // (닉네임, 프로필 이미지 URL 등을 위함)

                return ResponseEntity.ok(response);

            } else {
                // 4. 로그인 실패 (아이디 또는 비밀번호 불일치)
                // (보안을 위해 "아이디가 틀렸습니다" 또는 "비밀번호가 틀렸습니다"라고 구체적으로 알려주지 않는 것이 좋습니다)
                return new ResponseEntity<>("아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("로그인 중 서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- [기존] 중복 확인 API ---

    // 아이디 중복 확인 API
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.isUsernameTaken(username));
    }

    // 이메일 중복 확인 API
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.isEmailTaken(email));
    }

    // 닉네임 중복 확인 API
    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(userService.isNicknameTaken(nickname));
    }
}