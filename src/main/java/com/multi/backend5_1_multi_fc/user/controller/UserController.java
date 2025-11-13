package com.multi.backend5_1_multi_fc.user.controller;

import com.multi.backend5_1_multi_fc.user.dto.UserDto;
import com.multi.backend5_1_multi_fc.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // [추가]

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @ModelAttribute UserDto userDto,
            @RequestParam(value = "profile_image_file", required = false) MultipartFile profileImageFile // HTML의 파일 input name
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