package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.Room;
import com.triple_stack.route_in_backend.entity.RoomParticipant;
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

    public List<Room> getRoomListByUserId(Integer userId) {
        return roomMapper.getRoomListByUserId(userId);
    }

    public int deleteRoom(RoomParticipant roomParticipant) {
        return roomMapper.deleteRoom(roomParticipant);
    }

    public int changeRoomTitle(RoomParticipant roomParticipant) {
        return roomMapper.changeRoomTitle(roomParticipant);
    }

    public Optional<Room> getRoomParticipantByUserIdAndRoomId(RoomParticipant roomParticipant) {
        return roomMapper.getRoomParticipantByUserIdAndRoomId(roomParticipant);
    }

    public Optional<Room> getRoomByRoomId(Integer roomId) {
        return roomMapper.getRoomByRoomId(roomId);
    }
}
