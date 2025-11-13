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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    // 개인 일정 추가
    @PostMapping("/personal")
    public ResponseEntity<Void> addPersonal(@AuthenticationPrincipal UserDetails user,
                                            @RequestBody ScheduleDto.PersonalCreateReq req) {
        service.addPersonal(user.getUsername(), req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 개인 일정 수정
    @PutMapping("/personal/{scheduleId}")
    public ResponseEntity<Void> updatePersonal(@AuthenticationPrincipal UserDetails user,
                                               @PathVariable Long scheduleId,
                                               @RequestBody ScheduleDto.PersonalUpdateReq req) {
        service.updatePersonal(user.getUsername(), scheduleId, req);
        return ResponseEntity.ok().build();
    }

    // 개인 일정 삭제
    @DeleteMapping("/personal/{scheduleId}")
    public ResponseEntity<Void> deletePersonal(@AuthenticationPrincipal UserDetails user,
                                               @PathVariable Long scheduleId) {
        service.deletePersonal(user.getUsername(), scheduleId);
        return ResponseEntity.ok().build();
    }

    // 달력: 날짜별 상위 2개
    @GetMapping("/day/{date}/top2")
    public ResponseEntity<List<ScheduleDto.DayItem>> top2ByDate(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.top2ByDate(user.getUsername(), date));
    }

    // 달력: 날짜 전체
    @GetMapping("/day/{date}")
    public ResponseEntity<List<ScheduleDto.DayItem>> allByDate(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.allByDate(user.getUsername(), date));
    }

    // 메인 오늘 현재시각 이후 + 내일부터는 하루 전체 중 빠른 순으로 5개
    @GetMapping("/upcoming")
    public ResponseEntity<List<ScheduleDto.DayItem>> upcoming(
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(service.upcoming7Days(user.getUsername()));
    }

    // 경기 자동 연동(필요 시, 페이지 진입/수동 동기화 버튼에서 호출)
    @PostMapping("/sync-matches")
    public ResponseEntity<Integer> syncMatches(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(service.syncApprovedMatches(user.getUsername()));
    }

    // 공통 예외
    @ExceptionHandler(ScheduleException.class)
    public ResponseEntity<String> handleScheduleException(ScheduleException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
