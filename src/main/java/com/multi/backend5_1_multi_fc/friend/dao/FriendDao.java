package com.multi.backend5_1_multi_fc.friend.dao;

import com.multi.backend5_1_multi_fc.friend.dto.FriendDto;
import com.multi.backend5_1_multi_fc.friend.mapper.FriendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendDao {
    private final FriendMapper friendMapper;

    public Long findUserIdByUsername(String username) { return friendMapper.findUserIdByUsername(username); }

    public List<FriendDto.FriendListResponse> findMyFriends(Long meId, String keyword) {
        return friendMapper.findMyFriends(meId, keyword);
    }

    public List<FriendDto.FriendRequestResponse> findIncomingRequests(Long meId) {
        return friendMapper.findIncomingRequests(meId);
    }

    public List<FriendDto.FriendSearchResponse> searchUsers(Long meId, String keyword) {
        return friendMapper.searchUsers(meId, keyword);
    }

    public int deleteFriendBothWays(Long meId, Long targetId) {
        return friendMapper.deleteFriendBothWays(meId, targetId);
    }

    public int insertFriendRequest(Long requesterId, Long targetId) {
        return friendMapper.insertFriendRequest(requesterId, targetId);
    }

    public int approveFriend(Long meId, Long requesterId) {
        return friendMapper.approveFriend(meId, requesterId);
    }

    public int rejectFriend(Long meId, Long requesterId) {
        return friendMapper.rejectFriend(meId, requesterId);
    }

    public boolean existsRelationAny(Long a, Long b) {
        return friendMapper.existsRelationAny(a, b) > 0;
    }
}