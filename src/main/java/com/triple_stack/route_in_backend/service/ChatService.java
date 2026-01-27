package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.chat.*;
import com.triple_stack.route_in_backend.entity.Message;
import com.triple_stack.route_in_backend.entity.Room;
import com.triple_stack.route_in_backend.entity.RoomParticipant;
import com.triple_stack.route_in_backend.repository.MessageRepository;
import com.triple_stack.route_in_backend.repository.RoomRepository;
import com.triple_stack.route_in_backend.repository.UserRepository;
import com.triple_stack.route_in_backend.security.model.PrincipalUser;
import com.triple_stack.route_in_backend.utils.NotificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private NotificationUtils notificationUtils;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ApiRespDto<?> addRoom(AddRoomReqDto addRoomReqDto) {
        if (addRoomReqDto.getUserIds().isEmpty()) {
            throw new RuntimeException("1명 이상 있어야 합니다.");
        }

        Optional<Room> optionalRoom = roomRepository.addRoom(Room.builder().type(addRoomReqDto.getType()).build());
        if (optionalRoom.isEmpty()) {
            throw new RuntimeException("채팅방 생성에 실패했습니다");
        }

        Integer roomId = optionalRoom.get().getRoomId();
        for (Integer userId : addRoomReqDto.getUserIds()) {
            int result = roomRepository.addRoomParticipant(addRoomReqDto.toEntity(roomId, userId, "MEMBER"));
            if (result != 1) {
                throw new RuntimeException("채팅방 생성에 실패했습니다");
            }
        }

        return new ApiRespDto<>("success", "채팅창 생성 성공!", null);
    }

    public ApiRespDto<?> getRoomListByUserId(Integer userId) {
        return new ApiRespDto<>("success", "채팅방 목록 조회", roomRepository.getRoomListByUserId(userId));
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

        int result = messageRepository.addMessage(addMessageReqDto.toMessageEntity());
        if (result != 1) {
            throw new RuntimeException("메시지 전송에 실패했습니다");
        }

        int roomResult = roomRepository.changeRoomLastMessage(addMessageReqDto.toRoomEntity());
        if (roomResult != 1) {
            throw new RuntimeException("메시지 전송에 실패했습니다.");
        }

        List<RoomParticipant> roomParticipants = optionalRoom.get().getParticipants();
        RoomParticipant roomParticipant = roomParticipants.stream()
                .filter(r -> r.getUserId().equals(addMessageReqDto.getSenderId()))
                .findFirst().orElse(null);

        if (roomParticipant == null) {
            throw new RuntimeException("메시지 전송에 실패했습니다");
        }

        List<Integer> userIds = roomParticipants.stream().map(RoomParticipant::getUserId).toList();
        notificationUtils.sendAndAddNotification(userIds, roomParticipant.getTitle(),
                addMessageReqDto.getContent(), "/chat/room/"+addMessageReqDto.getRoomId(),
                userRepository.getUserByUserId(addMessageReqDto.getSenderId()).get().getProfileImg());

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

    public ApiRespDto<?> getMessageListInfinite(MessageInfiniteParam param) {
        if (param.getCursorMessageId() != null ^ param.getCursorCreateDt() != null) {
            throw new RuntimeException("cursorMessageId와 cursorCreateDt가 모두 전달되지 않았습니다");
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
