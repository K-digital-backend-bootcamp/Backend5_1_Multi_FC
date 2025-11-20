//package com.multi.backend5_1_multi_fc.chat.controller;
//
//import com.multi.backend5_1_multi_fc.chat.dto.*;
//import com.multi.backend5_1_multi_fc.chat.service.ChatService;
//import com.multi.backend5_1_multi_fc.security.CustomUserDetails;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.nio.file.AccessDeniedException;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class ChatController {
//    private final ChatService chatService;
//
//    private static final Long TEST_USER_ID = 41L;  // 테스트할 사용자 ID
//    private static final String TEST_USERNAME = "tester4";
//
//    //1. 1대 1 채팅방 생성
//    @PostMapping("/chatroom/onetoone")
//    public ChatRoomDto createOneToOneChatRoom(
//            @RequestParam("targetUserId") Long targetUserId,
//            @AuthenticationPrincipal CustomUserDetails userDetails
//    ) {
//
//        // 테스트 용
//        Long userId = (userDetails != null) ? userDetails.getUserId() : TEST_USER_ID;
//        System.out.println("✅ 1:1 채팅방 생성 요청");
//        System.out.println("  - userId: " + userId + " (테스트 모드)");
//        System.out.println("  - targetUserId: " + targetUserId);
//
//        return chatService.createOrGetOneToOneChatRoom(userId, targetUserId);
//    }
//
//    //2. 그룹 채팅방 생성
//    @PostMapping("/chatroom/group")
//    public ChatRoomDto createChatRoom(
//            @RequestBody CreateGroupChatRequest request,
//            @AuthenticationPrincipal CustomUserDetails userDetails){
//
//        //테스트
//        Long userId = (userDetails != null) ? userDetails.getUserId() : TEST_USER_ID;
//
//        return chatService.createGroupChatRoom(userId, request);
//    }
//
//    //3. 사용자의 채팅방 목록 조회 (타입별) - 동적 이름 포함
//    @GetMapping("/chatroom")
//    public List<ChatRoomWithParticipantDto> getChatRooms(
//            @RequestParam("type") String roomType,
//            @AuthenticationPrincipal CustomUserDetails userDetails){
//
//        Long userId = (userDetails != null) ? userDetails.getUserId() : TEST_USER_ID; // 테스트 용
//
//        return chatService.getUserChatRoomsByType(userId, roomType);
//    }
//
//    //4. 채팅방 단일 조회 (방의 정보를 응답받는 형태 - 방 제목, 멤버 )
//    @GetMapping("/chatroom/{id}")
//    public ChatRoomDto getChatRoom(@PathVariable("id") Long roomId){
//        return chatService.findChatRoomById(roomId);
//    }
//
//    //5. 채팅방 메세지 목록
//    @GetMapping("/chatroom/{id}/messages")
//    public List<ChatMessageDto> getChatMessages(@PathVariable("id") Long roomId, @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
//        Long userId = (userDetails != null) ? userDetails.getUserId() : TEST_USER_ID; // 테스트 용
//        return chatService.getMessagesByRoomId(userId, roomId);
//    }
//
//    //6. 채팅방 참가자 목록
//    @GetMapping("/chatroom/{id}/participants")
//    public List<ChatParticipantDto> getChatParticipants(@PathVariable("id") Long roomId){
//        return chatService.getParticipantsByRoomId(roomId);
//    }
//
//    //7. 채팅방 참가자 추가
//    @PostMapping("/chatroom/{id}/invite")
//    public void inviteParticipants(@PathVariable("id") Long roomId, @RequestBody List<Long> invitedUserIds){
//
//        for(Long userId: invitedUserIds){
//            chatService.addParticipant(createParticipantDto(roomId, userId));
//        }
//    }
//
//    private ChatParticipantDto createParticipantDto(Long roomId, Long userId) {
//        return ChatParticipantDto.builder()
//                .roomId(roomId)
//                .userId(userId)
//                .build();
//    }
//
//
//    //8. 채팅방 나가기
//    @DeleteMapping("/chatroom/{id}/leave")
//    public void leaveChatRoom(@PathVariable("id") Long roomId, @AuthenticationPrincipal CustomUserDetails userDetails){
//        chatService.leaveChatRoom(userDetails.getUserId(), roomId);
//    }
//
//    //8. 채팅 전송 기능
//    @MessageMapping("/chatroom/{roomId}/send")
//    public void sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageDto messageDto, @AuthenticationPrincipal CustomUserDetails userDetails){
//
//        //테스트
//        String username = (userDetails != null) ? userDetails.getUsername() : TEST_USERNAME;
//        messageDto.setRoomId(roomId);
//        messageDto.setSenderId(TEST_USER_ID);
//        messageDto.setSenderNickname(username);
//
////        messageDto.setRoomId(roomId);
////        messageDto.setSenderId(userId);
////        messageDto.setSenderNickname(username);
//        chatService.sendMessage(messageDto);
//    }
//
//
//
////    //1. 1대 1 채팅방 생성
////    @PostMapping("/chatroom/onetoone")
////    public ChatRoomDto createOneToOneChatRoom(
////            @RequestParam("targetUserId") Long targetUserId,
////            @AuthenticationPrincipal CustomUserDetails userDetails) {
////        return chatService.createOrGetOneToOneChatRoom(userDetails.getUserId(), targetUserId);
////    }
////
////    //2. 그룹 채팅방 생성
////    @PostMapping("/chatroom/group")
////    public ChatRoomDto createChatRoom(
////            @RequestBody CreateGroupChatRequest request,
////            @AuthenticationPrincipal CustomUserDetails userDetails){
////        return chatService.createGroupChatRoom(userDetails.getUserId(), request);
////    }
////
////    //3. 사용자의 채팅방 목록 조회 (타입별) - 동적 이름 포함
////    @GetMapping("/chatroom")
////    public List<ChatRoomWithParticipantDto> getChatRooms(
////            @RequestParam("type") String roomType,
////            @AuthenticationPrincipal CustomUserDetails userDetails){
////        return chatService.getUserChatRoomsByType(userDetails.getUserId(), roomType);
////    }
////
////    //4. 채팅방 단일 조회 (방의 정보를 응답받는 형태 - 방 제목, 멤버 )
////    @GetMapping("/chatroom/{id}")
////    public ChatRoomDto getChatRoom(@PathVariable("id") Long roomId){
////        return chatService.findChatRoomById(roomId);
////    }
////
////    //5. 채팅방 메세지 목록
////    @GetMapping("/chatroom/{id}/messages")
////    public List<ChatMessageDto> getChatMessages(@PathVariable("id") Long roomId, @AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
////        return chatService.getMessagesByRoomId(userDetails.getUserId(), roomId);
////    }
////
////    //6. 채팅방 참가자 목록
////    @GetMapping("/chatroom/{id}/participants")
////    public List<ChatParticipantDto> getChatParticipants(@PathVariable("id") Long roomId){
////        return chatService.getParticipantsByRoomId(roomId);
////    }
////
////    //7. 채팅방 참가자 추가
////    @PostMapping("/chatroom/{id}/invite")
////    public void inviteParticipants(@PathVariable("id") Long roomId, @RequestBody List<Long> invitedUserIds){
////
////        for(Long userId: invitedUserIds){
////            chatService.addParticipant(createParticipantDto(roomId, userId));
////        }
////    }
////
////    private ChatParticipantDto createParticipantDto(Long roomId, Long userId) {
////        return ChatParticipantDto.builder()
////                .roomId(roomId)
////                .userId(userId)
////                .build();
////    }
////
////
////    //8. 채팅방 나가기
////    @DeleteMapping("/chatroom/{id}/leave")
////    public void leaveChatRoom(@PathVariable("id") Long roomId, @AuthenticationPrincipal CustomUserDetails userDetails){
////        chatService.leaveChatRoom(userDetails.getUserId(), roomId);
////    }
////
////    //8. 채팅 전송 기능
////    @MessageMapping("/chatroom/{roomId}/send")
////    public void sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageDto messageDto, SimpMessageHeaderAccessor headerAccessor){
////        messageDto.setRoomId(roomId);
////        chatService.sendMessage(messageDto);
////    }
//}