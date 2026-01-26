package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.message.AddMessageReqDto;
import com.triple_stack.route_in_backend.dto.message.AddRoomReqDto;
import com.triple_stack.route_in_backend.dto.message.ChangeRoomTitleReqDto;
import com.triple_stack.route_in_backend.dto.message.DeleteRoomReqDto;
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
    public ApiRespDto<?> addRoom(AddRoomReqDto addRoomReqDto, PrincipalUser principalUser) {
        if (addRoomReqDto.getUserIds().isEmpty()) {
            throw new RuntimeException("1명 이상 있어야 합니다.");
        }

        if (!addRoomReqDto.getUserIds().contains(principalUser.getUserId())) {
            throw new RuntimeException("잘못된 접근입니다.");
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

    public ApiRespDto<?> deleteRoom(DeleteRoomReqDto deleteRoomReqDto) {
        Optional<Room> foundRoom = roomRepository.getRoomParticipantByUserIdAndRoomId(deleteRoomReqDto.toEntity());
        if (foundRoom.isEmpty()) {
            throw new RuntimeException("채팅방이 존재하지 않습니다");
        }

        int result = roomRepository.deleteRoom(deleteRoomReqDto.toEntity());
        if (result != 1) {
            throw new RuntimeException("채팅방 나가기에 실패했습니다");
        }

        return new ApiRespDto<>("success", "채팅방 나기기 완료", null);
    }

    public ApiRespDto<?> changeRoomTitle(ChangeRoomTitleReqDto changeRoomTitleReqDto) {
        Optional<Room> foundRoom = roomRepository.getRoomParticipantByUserIdAndRoomId(changeRoomTitleReqDto.toEntity());
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
}
