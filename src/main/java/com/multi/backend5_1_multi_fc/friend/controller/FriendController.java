package com.multi.backend5_1_multi_fc.friend.controller;

import com.multi.backend5_1_multi_fc.friend.dto.FriendDto;
import com.multi.backend5_1_multi_fc.friend.exception.FriendException;
import com.multi.backend5_1_multi_fc.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    /** 마이페이지와 동일한 더미 토큰 파싱 */
    private String getUsernameFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new FriendException("로그인이 필요한 요청입니다.");
        }
        String token = authHeader.substring("Bearer ".length());
        String prefix = "dummy-jwt-token-for-";
        if (!token.startsWith(prefix)) {
            throw new FriendException("유효하지 않은 토큰입니다.");
        }
        return token.substring(prefix.length());
    }

    /** 친구 목록 조회 (+ 닉네임 키워드 검색)  GET /api/friends?keyword= */
    @GetMapping
    public ResponseEntity<List<FriendDto.FriendListResponse>> myFriends(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam(required = false) String keyword
    ) {
        String username = getUsernameFromToken(authHeader);
        return ResponseEntity.ok(friendService.myFriends(username, keyword));
    }

    /** 받은 친구 요청 목록  GET /api/friends/requests */
    @GetMapping("/requests")
    public ResponseEntity<List<FriendDto.FriendRequestResponse>> incoming(
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        String username = getUsernameFromToken(authHeader);
        return ResponseEntity.ok(friendService.incomingRequests(username));
    }

    /** 친구 검색(아이디/닉네임)  GET /api/friends/search?keyword= */
    @GetMapping("/search")
    public ResponseEntity<List<FriendDto.FriendSearchResponse>> search(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam String keyword
    ) {
        String username = getUsernameFromToken(authHeader);
        return ResponseEntity.ok(friendService.searchUsersForFriend(username, keyword));
    }

    /** 친구 삭제  DELETE /api/friends/{targetUserId} */
    @DeleteMapping("/{targetUserId}")
    public ResponseEntity<?> deleteFriend(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long targetUserId
    ) {
        String username = getUsernameFromToken(authHeader);
        friendService.deleteFriend(username, targetUserId);
        return ResponseEntity.noContent().build();
    }

    /** 친구 요청 보내기  POST /api/friends/requests { targetUserId } */
    @PostMapping("/requests")
    public ResponseEntity<?> sendRequest(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody FriendDto.SendFriendRequest body
    ) {
        String username = getUsernameFromToken(authHeader);
        if (body == null || body.getTargetUserId() == null) {
            throw new FriendException("targetUserId가 필요합니다.");
        }
        friendService.sendFriendRequest(username, body.getTargetUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "친구 요청 전송"));
    }

    /** 친구 요청 수락  POST /api/friends/requests/{requesterUserId}/accept */
    @PostMapping("/requests/{requesterUserId}/accept")
    public ResponseEntity<?> accept(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long requesterUserId
    ) {
        String username = getUsernameFromToken(authHeader);
        friendService.acceptRequest(username, requesterUserId);
        return ResponseEntity.noContent().build();
    }

    /** 친구 요청 거절  POST /api/friends/requests/{requesterUserId}/reject */
    @PostMapping("/requests/{requesterUserId}/reject")
    public ResponseEntity<?> reject(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long requesterUserId
    ) {
        String username = getUsernameFromToken(authHeader);
        friendService.rejectRequest(username, requesterUserId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(FriendException.class)
    public ResponseEntity<?> handleFriendException(FriendException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }
}
