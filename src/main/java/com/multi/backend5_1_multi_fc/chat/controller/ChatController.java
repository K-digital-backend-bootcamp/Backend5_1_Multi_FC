package com.multi.backend5_1_multi_fc.chat.controller;

import com.multi.backend5_1_multi_fc.chat.dto.ChatMessageDto;
import com.multi.backend5_1_multi_fc.chat.dto.ChatParticipantDto;
import com.multi.backend5_1_multi_fc.chat.dto.ChatRoomDto;
import com.multi.backend5_1_multi_fc.chat.dto.ChatRoomWithParticipantDto;
import com.multi.backend5_1_multi_fc.chat.service.ChatService;
import com.multi.backend5_1_multi_fc.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/chatroom/onetoone")
    public ChatRoomDto createOneToOneChatRoom(
            @RequestParam("targetUserId") Long targetUserId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return chatService.createOrGetOneToOneChatRoom(userDetails.getUserId(), targetUserId);
    }

    //1. 그룹 채팅방 생성
    @PostMapping("/chatroom/group")
    public void createChatRoom(@RequestBody ChatRoomDto chatRoomDto){
        chatService.createChatRoom(chatRoomDto);
    }

    //사용자의 채팅방 목록 조회 (타입별) - 동적 이름 포함
    @GetMapping("/chatroom")
    public List<ChatRoomWithParticipantDto> getChatRooms(
            @RequestParam("type") String roomType,
            @AuthenticationPrincipal CustomUserDetails userDetails){
        return chatService.getUserChatRoomsByType(userDetails.getUserId(), roomType);
    }

    //2. 채팅방 단일 조회 (방의 정보를 응답받는 형태 - 방 제목, 멤버 )
    @GetMapping("/chatroom/{id}")
    public ChatRoomDto getChatRoom(@PathVariable("id") Long roomId){
        return chatService.findChatRoomById(roomId);
    }

    //3. 채팅방 메세지 목록
    @GetMapping("/chatroom/{id}/messages")
    public List<ChatMessageDto> getChatMessages(@PathVariable("id") Long roomId){
        return chatService.getMessagesByRoomId(roomId);
    }

    //4. 채팅방 참가자 목록
    @GetMapping("/chatroom/{id}/participants")
    public List<ChatParticipantDto> getChatParticipants(@PathVariable("id") Long roomId){
        return chatService.getParticipantsByRoomId(roomId);
    }

    //5. 채팅방 참가자 추가
    @PostMapping("/chatroom/{id}}/invite")
    public void inviteParticipant(@PathVariable("id") Long roomId, @RequestBody ChatParticipantDto participant){
        participant.setRoomId(roomId);
        chatService.addParticipant(participant);
    }

    //채팅 전송 기능
    @MessageMapping("/chatroom/{roomId}/send")
    public void sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageDto messageDto, SimpMessageHeaderAccessor headerAccessor){
        messageDto.setRoomId(roomId);
        chatService.sendMessage(messageDto);
    }
}
