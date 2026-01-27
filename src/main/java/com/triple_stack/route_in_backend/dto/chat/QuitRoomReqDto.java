package com.triple_stack.route_in_backend.dto.chat;

import com.triple_stack.route_in_backend.entity.RoomParticipant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuitRoomReqDto {
    private Integer userId;
    private Integer roomId;

    public RoomParticipant toEntity() {
        return RoomParticipant.builder()
                .userId(userId)
                .roomId(roomId)
                .build();
    }
}
