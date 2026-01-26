package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.Room;
import com.triple_stack.route_in_backend.entity.RoomParticipant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoomMapper {
    int addRoom(Room room);
    int addRoomParticipant(RoomParticipant roomParticipant);
    List<Room> getRoomListByUserId(Integer userId);
    int deleteRoom(RoomParticipant roomParticipant);
    int changeRoomTitle(RoomParticipant roomParticipant);
    Optional<Room> getRoomParticipantByUserIdAndRoomId(RoomParticipant roomParticipant);
    Optional<Room> getRoomByRoomId(Integer roomId);
}
