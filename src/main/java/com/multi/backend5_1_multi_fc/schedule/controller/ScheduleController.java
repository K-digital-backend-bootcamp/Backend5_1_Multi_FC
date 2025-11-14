
package com.multi.backend5_1_multi_fc.schedule.controller;

import com.multi.backend5_1_multi_fc.schedule.dto.ScheduleDto;
import com.multi.backend5_1_multi_fc.schedule.exception.ScheduleException;
import com.multi.backend5_1_multi_fc.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 1) username 추출 공통 메서드
    private String resolveUsername(
            UserDetails userDetails,
            String authHeader,
            String usernameHeader
    ) {
        // 1) 시큐리티에 이미 로그인 정보가 있으면 그걸 최우선 사용
        if (userDetails != null) {
            return userDetails.getUsername();
        }

        // 더미 토큰 prefix
        final String DUMMY_PREFIX = "dummy-jwt-token-for-";

        // 2) 프론트에서 보낸 X-USERNAME (encodeURIComponent 로 인코딩된 값) 디코딩
        if (usernameHeader != null && !usernameHeader.isBlank()) {
            String decoded = URLDecoder.decode(usernameHeader, StandardCharsets.UTF_8);
            // dummy-jwt-token-for-taeran0129 형태면 뒤에 부분만 username 으로 사용
            if (decoded.startsWith(DUMMY_PREFIX)) {
                return decoded.substring(DUMMY_PREFIX.length());
            }
            return decoded;
        }

        // 3) Authorization: Bearer <token> 에도 같은 패턴 지원
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            if (token.startsWith(DUMMY_PREFIX)) {
                return token.substring(DUMMY_PREFIX.length());
            }
            return token; // 옛날 "Bearer username" 방식도 그대로 지원
        }

        // 4) 그래도 없으면 예외
        throw new ScheduleException("사용자를 찾을 수 없습니다.");
    }
    // 2) 개인 일정 추가
    @PostMapping("/personal")
    public ResponseEntity<Void> addPersonal(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-USERNAME", required = false) String usernameHeader,
            @RequestBody ScheduleDto.PersonalCreateReq req
    ) {
        String username = resolveUsername(userDetails, authHeader, usernameHeader);
        scheduleService.addPersonal(username, req);
        return ResponseEntity.ok().build();
    }

    // 3) 개인 일정 수정
    @PutMapping("/personal/{scheduleId}")
    public ResponseEntity<Void> updatePersonal(
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-USERNAME", required = false) String usernameHeader,
            @RequestBody ScheduleDto.PersonalUpdateReq req
    ) {
        String username = resolveUsername(userDetails, authHeader, usernameHeader);
        scheduleService.updatePersonal(username, scheduleId, req);
        return ResponseEntity.ok().build();
    }

    // 4) 개인 일정 삭제
    @DeleteMapping("/personal/{scheduleId}")
    public ResponseEntity<Void> deletePersonal(
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-USERNAME", required = false) String usernameHeader

    ) {
        String username = resolveUsername(userDetails, authHeader, usernameHeader);
        scheduleService.deletePersonal(username, scheduleId);
        return ResponseEntity.ok().build();
    }

    // 5) 특정 달 전체 일정 조회
    @GetMapping("/month")
    public ResponseEntity<List<ScheduleDto.DayItem>> getMonthSchedules(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-USERNAME", required = false) String usernameHeader,
            @RequestParam int year,
            @RequestParam int month
    ) {
        String username = resolveUsername(userDetails, authHeader, usernameHeader);

        // 경기 자동 연동
        scheduleService.syncApprovedMatches(username);

        YearMonth ym = YearMonth.of(year, month);
        List<ScheduleDto.DayItem> list = scheduleService.allByMonth(username, ym);

        return ResponseEntity.ok(list);
    }

    // 6) 특정 날짜 전체 일정 조회
    @GetMapping("/day")
    public ResponseEntity<List<ScheduleDto.DayItem>> getDaySchedules(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-USERNAME", required = false) String usernameHeader,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        String username = resolveUsername(userDetails, authHeader, usernameHeader);
        // 1) 경기 일정 먼저 동기화 (Match → Schedule)
        scheduleService.syncApprovedMatches(username);

        // 2) 그 날짜의 전체 일정 조회 (개인 일정 + 경기 일정 모두 포함)
        List<ScheduleDto.DayItem> result = scheduleService.allByDate(username, date);
        return ResponseEntity.ok(result);
    }

    // 7) 메인 페이지용 - 7일 이내 일정 조회
    @GetMapping("/upcoming")
    public ResponseEntity<List<ScheduleDto.DayItem>> getUpcoming7Days(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestHeader(value = "X-USERNAME", required = false) String usernameHeader
    ) {
        // 1) username 구하기 (기존 공통 메서드 그대로 사용)
        String username = resolveUsername(userDetails, authHeader, usernameHeader);
        System.out.println("[DEBUG] /api/schedule/upcoming username = " + username);

        // 2) 먼저 경기 일정 자동 동기화 (Match → Schedule)
        scheduleService.syncApprovedMatches(username);

        // 3) 그 다음에 7일 이내 일정 조회
        List<ScheduleDto.DayItem> result = scheduleService.upcoming7Days(username);

        // 4) 프론트로 리턴
        return ResponseEntity.ok(result);
    }
}
