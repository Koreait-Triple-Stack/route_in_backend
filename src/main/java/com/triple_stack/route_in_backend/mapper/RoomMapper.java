package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.dto.chat.RoomRespDto;
import com.triple_stack.route_in_backend.entity.Room;
import com.triple_stack.route_in_backend.entity.RoomParticipant;
import com.triple_stack.route_in_backend.entity.RoomRead;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoomMapper {
    int addRoom(Room room);
    int addRoomParticipant(RoomParticipant roomParticipant);
    List<RoomRespDto> getRoomListByUserId(Integer userId);
    int quitRoom(RoomParticipant roomParticipant);
    int changeRoomTitle(RoomParticipant roomParticipant);
    Optional<RoomParticipant> getRoomParticipantByUserIdAndRoomId(Integer roomId, Integer userId);
    Optional<Room> getRoomByRoomId(Integer roomId);
    int changeRoomLastMessage(Room room);
    int addRoomRead(RoomRead roomRead);
    int changeRoomRead(RoomRead roomRead);
    int cancelQuitRoom(RoomParticipant roomParticipant);
    int countUnreadChatByUserId(Integer userId);
}
