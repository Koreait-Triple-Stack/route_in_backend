package com.triple_stack.route_in_backend.dto.chat;

import com.triple_stack.route_in_backend.entity.Message;
import com.triple_stack.route_in_backend.entity.Room;
import com.triple_stack.route_in_backend.entity.RoomParticipant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuitRoomReqDto {
    private Integer userId;
    private Integer roomId;
    private String username;
    private List<Integer> participantIds;
    private List<String> participantNames;

    public RoomParticipant toParticipantEntity() {
        return RoomParticipant.builder()
                .userId(userId)
                .roomId(roomId)
                .build();
    }

    public Message toMessageEntity() {
        return Message.builder()
                .roomId(roomId)
                .senderId(null)
                .type("TEXT")
                .content(username+"님이 방을 나갔습니다")
                .build();
    }
}
