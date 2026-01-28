package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.chat.*;
import com.triple_stack.route_in_backend.entity.*;
import com.triple_stack.route_in_backend.repository.MessageRepository;
import com.triple_stack.route_in_backend.repository.RoomRepository;
import com.triple_stack.route_in_backend.repository.UserRepository;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.utils.NotificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private NotificationUtils notificationUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ApiRespDto<?> addRoom(AddRoomReqDto addRoomReqDto, PrincipalUser principalUser) {
        if (addRoomReqDto.getUserIds().isEmpty()) {
            throw new RuntimeException("1명 이상 있어야 합니다.");
        }

        Integer profileUserId = addRoomReqDto.getUserIds().stream().filter(id -> id.equals(principalUser.getUserId())).findFirst().orElse(null);
        String type = addRoomReqDto.getUserIds().size() > 2 ? "GROUP" : "DM";
        Room room = Room.builder()
                .type(type)
                .profileUserId(profileUserId)
                .build();
        Optional<Room> optionalRoom = roomRepository.addRoom(room);
        if (optionalRoom.isEmpty()) {
            throw new RuntimeException("채팅방 생성에 실패했습니다");
        }

        Integer roomId = optionalRoom.get().getRoomId();
        for (Integer userId : addRoomReqDto.getUserIds()) {
            int result = roomRepository.addRoomParticipant(addRoomReqDto.toEntity(roomId, userId, "MEMBER"));
            if (result != 1) {
                throw new RuntimeException("채팅방 생성에 실패했습니다");
            }

            int readResult = roomRepository.addRoomRead(RoomRead.builder().roomId(roomId).userId(userId).build());
            if (readResult != 1) {
                throw new RuntimeException("채팅방 생성에 실패했습니다");
            }
        }

        return new ApiRespDto<>("success", "채팅창 생성 성공!", roomId);
    }

    public ApiRespDto<?> getRoomListByUserId(Integer userId) {
        return new ApiRespDto<>("success", "채팅방 목록 조회", roomRepository.getRoomListByUserId(userId));
    }

    @Transactional
    public ApiRespDto<?> getRoomByRoomId(Integer roomId, PrincipalUser principalUser) {
        Optional<Room> foundRoom = roomRepository.getRoomByRoomId(roomId);
        if (foundRoom.isEmpty()) {
            throw new RuntimeException("채팅방 조회 실패");
        }

        RoomRead roomRead = RoomRead.builder()
                .roomId(roomId)
                .userId(principalUser.getUserId())
                .lastReadMessageId(foundRoom.get().getLastMessageId())
                .build();

        int result = roomRepository.changeRoomRead(roomRead);
        if (result != 1) throw new RuntimeException("읽음 처리 실패");

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                messagingTemplate.convertAndSend(
                        "/topic/room/" + roomId,
                        Map.of(
                                "type", "read",
                                "roomId", roomId,
                                "userId", principalUser.getUserId()
                        )
                );
            }
        });

        return new ApiRespDto<>("success", "채팅방 상세 조회 완료", foundRoom.get());
    }

    public ApiRespDto<?> quitRoom(QuitRoomReqDto quitRoomReqDto) {
        Optional<RoomParticipant> foundRoom = roomRepository.getRoomParticipantByUserIdAndRoomId(quitRoomReqDto.toEntity());
        if (foundRoom.isEmpty()) {
            throw new RuntimeException("채팅방이 존재하지 않습니다");
        }

        int result = roomRepository.quitRoom(quitRoomReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("채팅방 나가기에 실패했습니다");
        }

        return new ApiRespDto<>("success", "채팅방 나기기 완료", null);
    }

    public ApiRespDto<?> changeRoomTitle(ChangeRoomTitleReqDto changeRoomTitleReqDto) {
        Optional<RoomParticipant> foundRoom = roomRepository.getRoomParticipantByUserIdAndRoomId(changeRoomTitleReqDto.toEntity());
        if (foundRoom.isEmpty()) {
            throw new RuntimeException("채팅방이 존재하지 않습니다");
        }

        int result = roomRepository.changeRoomTitle(changeRoomTitleReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("채팅방 나가기에 실패했습니다");
        }

        return new ApiRespDto<>("success", "채팅방 제목 수정 완료", null);
    }

    @Transactional
    public ApiRespDto<?> addMessage(AddMessageReqDto addMessageReqDto) {
        Optional<Room> optionalRoom = roomRepository.getRoomByRoomId(addMessageReqDto.getRoomId());
        if (optionalRoom.isEmpty()) {
            throw new RuntimeException("채팅방이 존재하지 않습니다");
        }

        Optional<Message> optionalMessage = messageRepository.addMessage(addMessageReqDto.toMessageEntity());
        if (optionalMessage.isEmpty()) {
            throw new RuntimeException("메시지 전송에 실패했습니다");
        }

        int roomResult = roomRepository.changeRoomLastMessage(addMessageReqDto.toRoomEntity(optionalMessage.get().getMessageId()));
        if (roomResult != 1) {
            throw new RuntimeException("메시지 전송에 실패했습니다.");
        }

        List<RoomParticipant> roomParticipants = optionalRoom.get().getParticipants();

        if (roomParticipants.isEmpty()) {
            throw new RuntimeException("메시지 전송에 실패했습니다");
        }

        List<Notification> notifications = new ArrayList<>();
        for (RoomParticipant participant : roomParticipants) {
            notifications.add(Notification.builder()
                    .userId(participant.getUserId())
                    .title(participant.getTitle())
                    .message(addMessageReqDto.getContent())
                    .path("/chat/room/"+addMessageReqDto.getRoomId())
                    .profileImg(userRepository.getUserByUserId(addMessageReqDto.getSenderId()).get().getProfileImg())
                    .build());
        }

        notificationUtils.sendAndAddNotification(notifications, addMessageReqDto.getRoomId(), addMessageReqDto.getSenderId());
        messagingTemplate.convertAndSend(
                "/topic/room/" + optionalRoom.get().getRoomId(),
                Map.of(
                        "type", "MESSAGE",
                        "roomId", optionalRoom.get().getRoomId(),
                        "userId", addMessageReqDto.getSenderId()
                )
        );

        return new ApiRespDto<>("success", "메시지 전송 완료", null);
    }

    public ApiRespDto<?> changeMessage(ChangeMessageReqDto changeMessageReqDto) {
        Optional<Message> foundMessage = messageRepository.getMessageByMessageId(changeMessageReqDto.getMessageId());
        if (foundMessage.isEmpty()) {
            throw new RuntimeException("존재하지 않는 메시지 입니다");
        }

        if (foundMessage.get().getSenderId() == null) {
            throw new RuntimeException("이미 삭제된 메시지 입니다.");
        }

        int result = messageRepository.changeMessage(changeMessageReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("메시지 수정을 실패했습니다");
        }

        return new ApiRespDto<>("success", "메시지 수정 완료", null);
    }

    public ApiRespDto<?> deleteMessage(Integer messageId) {
        Optional<Message> foundMessage = messageRepository.getMessageByMessageId(messageId);
        if (foundMessage.isEmpty()) {
            throw new RuntimeException("존재하지 않는 메시지 입니다");
        }

        if (foundMessage.get().getSenderId() == null) {
            throw new RuntimeException("이미 삭제된 메시지 입니다.");
        }

        Message message = Message.builder()
                .messageId(messageId)
                .senderId(null)
                .content("삭제된 메시지입니다")
                .build();
        int result = messageRepository.changeMessage(message);
        if (result != 1) {
            throw new RuntimeException("메시지 삭제를 실패했습니다");
        }

        return new ApiRespDto<>("success", "메시지 삭제 완료", null);
    }

    @Transactional
    public ApiRespDto<?> getMessageListInfinite(MessageInfiniteParam param, PrincipalUser principalUser) {
        if (param.getCursorMessageId() != null ^ param.getCursorCreateDt() != null) {
            throw new RuntimeException("cursorMessageId와 cursorCreateDt가 모두 전달되지 않았습니다");
        }

        int lastMessageId = messageRepository.getLastMessageIdByRoomId(param.getRoomId()).orElse(0);
        if (param.getCursorMessageId() == null && param.getCursorCreateDt() == null) {
            RoomRead roomRead = RoomRead.builder()
                    .roomId(param.getRoomId())
                    .userId(principalUser.getUserId())
                    .lastReadMessageId(lastMessageId)
                    .build();
            int result = roomRepository.changeRoomRead(roomRead);
            if (result != 1) {
                throw new RuntimeException("채팅방 조회 실패");
            }
        }

        param.setLimitPlusOne(param.getLimitPlusOne());

        List<Message> rows = messageRepository.getMessageListInfinite(param);
        boolean hasNext = rows.size() > param.getLimit();
        if (hasNext) {
            rows = rows.subList(0, param.getLimit());
        }

        MessageInfiniteRespDto data = new MessageInfiniteRespDto(rows, hasNext, null, null);
        if (!rows.isEmpty()) {
            Message last = rows.get(rows.size() - 1);
            data.setNextCursorMessageId(last.getMessageId());
            data.setNextCursorCreateDt(last.getCreateDt());
        }

        return new ApiRespDto<>("success", "메시지 무한스크롤 조회 완료", data);
    }
}
