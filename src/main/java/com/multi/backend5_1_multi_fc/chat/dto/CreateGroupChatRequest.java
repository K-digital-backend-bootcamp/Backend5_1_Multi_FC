package com.multi.backend5_1_multi_fc.chat.dto;


import lombok.Data;

import java.util.List;


//그룹 채팅 Dto
@Data
public class CreateGroupChatRequest {
    private String roomType;
    private String roomName;
    private List<Long> invitedUserIds;
}
