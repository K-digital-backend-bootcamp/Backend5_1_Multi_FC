package com.multi.backend5_1_multi_fc.chat.service;

import com.multi.backend5_1_multi_fc.chat.dao.ChatMessageDao;
import com.multi.backend5_1_multi_fc.chat.dao.ChatParticipantDao;
import com.multi.backend5_1_multi_fc.chat.dao.ChatRoomDao;
import com.multi.backend5_1_multi_fc.chat.dto.ChatMessageDto;
import com.multi.backend5_1_multi_fc.chat.dto.ChatParticipantDto;
import com.multi.backend5_1_multi_fc.chat.dto.ChatRoomDto;
import com.multi.backend5_1_multi_fc.chat.dto.ChatRoomWithParticipantDto;
import com.multi.backend5_1_multi_fc.notification.service.NotificationService;
import com.multi.backend5_1_multi_fc.user.dao.UserDao;
import com.multi.backend5_1_multi_fc.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomDao chatRoomDao;
    private final ChatMessageDao chatMessageDao;
    private  final ChatParticipantDao chatParticipantDao;
    private final UserDao userDao;
    private final RabbitTemplate rabbitTemplate;
    private final NotificationService notificationService;

    public List<ChatRoomWithParticipantDto> getUserChatRoomsByType(Long userId, String roomType){
        List<ChatRoomDto> rooms = chatRoomDao.findChatRoomsByUserIdAndType(userId, roomType);

        return rooms.stream().map(room -> {
            ChatRoomWithParticipantDto result = new ChatRoomWithParticipantDto();
            result.setRoomId(room.getRoomId());
            result.setRoomType(room.getRoomType());
            result.setMemberCount(room.getMemberCount());

            if("1대1".equals(room.getRoomType())){
                String dynamicRoomName = generateOneToOneRoomName(room.getRoomId(), userId);
                result.setRoomName(dynamicRoomName);
            } else {
                result.setRoomName(room.getRoomName());
            }

            return result;
        }).collect(Collectors.toList());
    }

    //1대1 채팅방 이름 동적 생성(상대방 닉네임)
    private String generateOneToOneRoomName(Long roomId, Long currentUserId){
        List<ChatParticipantDto> participants = chatParticipantDao.findParticipantsByRoomId(roomId);

        ChatParticipantDto opponent = participants.stream()
                .filter(p -> !p.getUserId().equals(currentUserId))
                .findFirst()
                .orElse(null);

        if(opponent != null){
            UserDto opponentUser = userDao.findByUserId(opponent.getUserId());
            return opponentUser.getNickname() + "님과의 채팅";
        }

        return "1대 1 채팅";
    }


    //채팅방 생성 (그룹 채팅용)
    public void createChatRoom(ChatRoomDto chatRoomDto){
        chatRoomDao.insertChatRoom(chatRoomDto);
    }

    //채팅방 조회
    public ChatRoomDto findChatRoomById(Long roomId){
        return chatRoomDao.findChatRoomById(roomId);
    }


    //1대 1 채팅방 버튼
    public ChatRoomDto createOrGetOneToOneChatRoom(Long userId1, Long userId2){
        ChatRoomDto existingRoom = chatRoomDao.findOneToOneChatRoom(userId1, userId2);
        Long currentLastRoom = chatRoomDao.getLastRoomId();
        if(existingRoom != null){
            return existingRoom;
        }

        UserDto targetUser = userDao.findByUserId(userId2);

        ChatRoomDto newRoom = ChatRoomDto.builder()
                .roomId(currentLastRoom + 1)
                .roomType("1대1")
                .roomName(targetUser.getNickname() + " 님과의 채팅")
                .memberCount(2)
                .build();

        chatRoomDao.insertChatRoom(newRoom);

        chatParticipantDao.insertParticipant(ChatParticipantDto.builder()
                .roomId(newRoom.getRoomId())
                .userId(userId1)
                .build()
        );

        chatParticipantDao.insertParticipant(ChatParticipantDto.builder()
                .roomId(newRoom.getRoomId())
                .userId(userId2)
                .build()
        );
        return newRoom;
    }



    //채팅 메세지 저장 + MQ publish
    public void sendMessage(ChatMessageDto messageDto){
        //DB 메세지 저장
        chatMessageDao.insertMessage(messageDto);

        //RabbitMQ로 publish
        rabbitTemplate.convertAndSend("chat.exchange", "room." + messageDto.getRoomId(), messageDto);

        ChatRoomDto chatRoom = chatRoomDao.findChatRoomById(messageDto.getRoomId());

        List<ChatParticipantDto> participants = chatParticipantDao.findParticipantsByRoomId(messageDto.getRoomId());

        for(ChatParticipantDto participant : participants){
            if(!participant.getUserId().equals(messageDto.getSenderId())){
                String notificationRoomName;
                if("1대1".equals(chatRoom.getRoomType())){
                    notificationRoomName = generateOneToOneRoomName(messageDto.getRoomId(), participant.getUserId());
                } else {
                    notificationRoomName = chatRoom.getRoomName();
                }

                notificationService.createOrUpdateChatNotification(
                        participant.getUserId(),
                        notificationRoomName,
                        messageDto.getRoomId()
                );
            }
        }
    }

    //채팅방 메세지 목록 조회
    public List<ChatMessageDto> getMessagesByRoomId(Long roomId){
        return chatMessageDao.findMessagesByRoomId(roomId);
    }

    //채팅방 참가자 목록 조회
    public List<ChatParticipantDto> getParticipantsByRoomId(Long roomId){
        return chatParticipantDao.findParticipantsByRoomId(roomId);
    }

    //채팅방 참가자 추가
    public void addParticipant(ChatParticipantDto participant){
        chatParticipantDao.insertParticipant(participant);

        //초대받은 사용자에게 알림
        ChatRoomDto chatRoom = chatRoomDao.findChatRoomById(participant.getRoomId());
        notificationService.createAndSendNotification(
                participant.getUserId(),
                chatRoom.getRoomName() + "채팅방에 초대되었습니다.",
                "채팅",
                participant.getRoomId()
        );
    }

    //채팅방 참가자 퇴장
    public void removeParticipant(Long roomId, Long chatPartId){
        chatParticipantDao.deleteParticipant(roomId,chatPartId);
    }
}
