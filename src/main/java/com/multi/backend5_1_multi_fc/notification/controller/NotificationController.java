package com.multi.backend5_1_multi_fc.notification.controller;

import com.multi.backend5_1_multi_fc.notification.dto.NotificationDto;
import com.multi.backend5_1_multi_fc.notification.service.NotificationService;
import com.multi.backend5_1_multi_fc.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    //GET /api/notifications
    //알림 목록 조회
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        Long userId = userDetails.getUserId();

        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    //GET /api/notifications/unread
    //읽지 않은 알림 조회
    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        return ResponseEntity.ok(notificationService.getUnreadNotifications(userId));
    }

    //GET /api/notifications/unread/count
    //읽지 않은 알림 개수
    @GetMapping("/unread/count")
    public ResponseEntity<Integer> getUnreadCount(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();

        return ResponseEntity.ok(notificationService.getUnreadCount(userId));
    }

    //PUT /api/notifications/read-all
    //전체 읽음 처리
    @PutMapping("read-all")
    public ResponseEntity<Void> markAllAsRead(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUserId();
        notificationService.markAllAsRead(userId);

        return ResponseEntity.ok().build();
    }

    //PUT /api/notifications/{id}/read
    //특정 알림 읽음 처리
    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markRead(@PathVariable Long id){
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    //DELETE /api/notifications/{id}
    //알림 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id){
        notificationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }
}
