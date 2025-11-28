package com.multi.backend5_1_multi_fc.friend.service;

import com.multi.backend5_1_multi_fc.friend.dto.FriendDto;
import com.multi.backend5_1_multi_fc.friend.exception.FriendException;
import com.multi.backend5_1_multi_fc.friend.repository.FriendRepo;
import com.multi.backend5_1_multi_fc.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepo repo;
    // 친구 요청 알림 추가
    private final NotificationService notificationService;

    private Long meId(String username) {
        Long id = repo.userIdOf(username);
        if (id == null) throw new FriendException("사용자를 찾을 수 없습니다.");
        return id;
    }

    public List<FriendDto.FriendListResponse> myFriends(String username, String keyword) {
        return repo.myFriends(meId(username), (keyword == null ? null : keyword.trim()));
    }

    public List<FriendDto.FriendRequestResponse> incomingRequests(String username) {
        return repo.incoming(meId(username));
    }

    public List<FriendDto.FriendSearchResponse> searchUsersForFriend(String username, String keyword) {
        if (keyword == null || keyword.isBlank()) return List.of();
        return repo.search(meId(username), keyword.trim());
    }

    @Transactional
    public void deleteFriend(String username, Long targetUserId) {
        Long me = meId(username);
        if (me.equals(targetUserId)) throw new FriendException("자기 자신은 삭제할 수 없습니다.");
        int affected = repo.deleteBoth(me, targetUserId);
        if (affected == 0) throw new FriendException("삭제할 친구 관계가 없습니다.");
    }

    @Transactional
    public void sendFriendRequest(String username, Long targetUserId) {
        Long me = meId(username);
        if (me.equals(targetUserId)) throw new FriendException("자기 자신에게는 요청할 수 없습니다.");

        // 거절 후 재요청 허용
        if (repo.existsAny(me, targetUserId)) throw new FriendException("이미 요청 중이거나 친구입니다.");

        int inserted = repo.sendRequest(me, targetUserId);
        if (inserted == 0) throw new FriendException("요청이 이미 존재합니다.");

        // 알림 생성 + WebSocket 전송
        String content = username + " 님이 친구 요청을 보냈습니다.";

        // targetUserId : 친구요청을 받은 사람 (알림 받을 사람)
        // me           : 친구요청을 보낸 사람 (requesterUserId)
        notificationService.createAndSendNotification(
                targetUserId,       // 알림 받을 유저 ID
                content,            // 알림 내용
                "친구신청",          // type → 프론트에서 이걸 기준으로 버튼/아이콘 처리
                me                  // referenceId = 요청 보낸 사람의 userId (requesterUserId)
        );
    }

    @Transactional
    public void acceptRequest(String username, Long requesterUserId) {
        Long me = meId(username); // me = 요청을 받은 사람(수락하는 사람)
        int updated = repo.accept(me, requesterUserId);
        if (updated == 0) throw new FriendException("수락할 요청이 없습니다.");

        // 친구요청 알림 처리 (읽음/삭제)
        notificationService.markFriendRequestNotificationHandled(me, requesterUserId);
    }

    @Transactional
    public void rejectRequest(String username, Long requesterUserId) {
        Long me = meId(username); // me = 요청을 받은 사람(거절하는 사람)
        int updated = repo.reject(me, requesterUserId);
        if (updated == 0) throw new FriendException("거절할 요청이 없습니다.");

        // 친구요청 알림 처리 (읽음/삭제)
        notificationService.markFriendRequestNotificationHandled(me, requesterUserId);
    }
}

