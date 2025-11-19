package com.multi.backend5_1_multi_fc.user.controller;

import com.multi.backend5_1_multi_fc.user.dto.UserDto;
import com.multi.backend5_1_multi_fc.user.service.UserService;
import com.multi.backend5_1_multi_fc.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // [기존] 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @ModelAttribute UserDto userDto,
            @RequestParam(value = "profile_image_file", required = false) MultipartFile profileImageFile
    ) {
        System.out.println("[요청 도착] DTO: " + userDto);
        System.out.println("[요청 도착] 파일: " + (profileImageFile != null ? profileImageFile.getOriginalFilename() : "없음"));

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

    // --- [로그인 기능 수정] ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        System.out.println("🔥🔥🔥 /api/users/login 요청 도착! 🔥🔥🔥");
        System.out.println("payload: " + payload);

        String username = payload.get("username");
        String rawPassword = payload.get("password");

        System.out.println("username: " + username);
        System.out.println("password: " + rawPassword);

        try {
            // 1. 서비스로 아이디/비번을 보내 인증 요청
            UserDto user = userService.login(username, rawPassword);

            System.out.println("userService.login() 결과: " + (user != null ? "성공" : "실패"));

            if (user != null) {
                // 2. 로그인 성공
                user.setPassword(null); // (보안) 응답에서 비밀번호 제거
                user.setResetCode(null);
                user.setResetCodeExpires(null);

                String realToken = jwtUtil.generateToken(user.getUsername());

                Map<String, Object> response = new HashMap<>();
                response.put("accessToken", realToken);
                response.put("user", user);

                System.out.println("✅ 로그인 성공 응답 반환");
                return ResponseEntity.ok(response);

            } else {
                System.out.println("❌ 로그인 실패: 아이디 또는 비밀번호 불일치");
                return new ResponseEntity<>("아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.err.println("❌ 로그인 중 예외 발생:");
            e.printStackTrace();
            return new ResponseEntity<>("로그인 중 서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
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


    @PostMapping("/find-id")
    public ResponseEntity<?> findId(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String maskedUsername = userService.findMyId(email);

            // 성공 시 (예: { "username": "fut***" })
            Map<String, String> response = new HashMap<>();
            response.put("username", maskedUsername);
            return ResponseEntity.ok(response);

        } catch (IllegalStateException e) {
            // 실패 시 (예: "일치하는 이메일 정보가 없습니다.")
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("아이디 찾기 중 서버 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 비밀번호 찾기 인증번호 요청
    @PostMapping("/reset-password/request")
    public ResponseEntity<String> requestPasswordReset(@RequestBody Map<String, String> payload) {
        try {
            String username = payload.get("username");
            String email = payload.get("email");
            userService.requestPasswordReset(username, email);

            // 성공 시
            return ResponseEntity.ok("인증코드가 이메일로 발송되었습니다. 메일함을 확인해주세요.");

        } catch (IllegalStateException e) {
            // [추가] 일치하는 정보가 없을 때
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("인증코드 발송 중 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 인증번호 검증
    @PostMapping("/reset-password/verify")
    public ResponseEntity<String> verifyPasswordResetCode(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String code = payload.get("code");
            userService.verifyPasswordResetCode(email, code);

            return ResponseEntity.ok("인증 성공");
        } catch (IllegalStateException e) {
            // (예: "인증코드가 올바르지 않거나 만료되었습니다.")
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("인증 중 서버 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 새 비밀번호로 변경
    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> confirmPasswordReset(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String code = payload.get("code");
            String newPassword = payload.get("newPassword");

            userService.confirmPasswordReset(email, code, newPassword);

            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (IllegalStateException e) {
            // (예: "인증코드가 올바르지 않거나 만료되었습니다.")
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("비밀번호 변경 중 서버 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인되지 않았습니다.");
        }

        UserDto user = userService.getUserProfile(userDetails.getUsername());

        // 보안을 위해 비밀번호는 제외하고 필요한 정보만 Map으로 반환
        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("nickname", user.getNickname());
        response.put("email", user.getEmail());
        response.put("profileImage", user.getProfileImage());

        return ResponseEntity.ok(response);
    }
}