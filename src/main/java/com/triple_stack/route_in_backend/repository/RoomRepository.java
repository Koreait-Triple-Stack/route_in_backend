package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.dto.chat.RoomRespDto;
import com.triple_stack.route_in_backend.entity.Room;
import com.triple_stack.route_in_backend.entity.RoomParticipant;
import com.triple_stack.route_in_backend.entity.RoomRead;
import com.triple_stack.route_in_backend.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomRepository {
    @Autowired
    private RoomMapper roomMapper;

    public Optional<Room> addRoom(Room room) {
        try {
            int result = roomMapper.addRoom(room);
            if (result != 1) {
                throw new RuntimeException("채팅방 생성에 실패했습니다");
            }
        } catch (RuntimeException e) {
            return Optional.empty();
        }

        return Optional.of(room);
    }

    public int addRoomParticipant(RoomParticipant roomParticipant) {
        return roomMapper.addRoomParticipant(roomParticipant);
    }

    public List<RoomRespDto> getRoomListByUserId(Integer userId) {
        return roomMapper.getRoomListByUserId(userId);
    }

    public int quitRoom(RoomParticipant roomParticipant) {
        return roomMapper.quitRoom(roomParticipant);
    }

    public int changeRoomTitle(RoomParticipant roomParticipant) {
        return roomMapper.changeRoomTitle(roomParticipant);
    }

    public Optional<RoomParticipant> getRoomParticipantByUserIdAndRoomId(Integer roomId, Integer userId) {
        return roomMapper.getRoomParticipantByUserIdAndRoomId(roomId, userId);
    }

    public Optional<Room> getRoomByRoomId(Integer roomId) {
        return roomMapper.getRoomByRoomId(roomId);
    }

    public int changeRoomLastMessage(Room room) {
        return roomMapper.changeRoomLastMessage(room);
    }

    public int addRoomRead(RoomRead roomRead) {
        return roomMapper.addRoomRead(roomRead);
    }

    public int changeRoomRead(RoomRead roomRead) {
        return roomMapper.changeRoomRead(roomRead);
    }

    public int cancelQuitRoom(RoomParticipant roomParticipant) {
        return roomMapper.cancelQuitRoom(roomParticipant);
    }

    public int countUnreadChatByUserId(Integer userId) {
        return roomMapper.countUnreadChatByUserId(userId);
    }

    public int muteNotification(RoomParticipant roomParticipant) {
        return roomMapper.muteNotification(roomParticipant);
    }

    public int changeRoomFavorite(RoomParticipant roomParticipant) {
        return roomMapper.changeRoomFavorite(roomParticipant);
    }
}
