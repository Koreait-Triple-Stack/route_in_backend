package com.triple_stack.route_in_backend.dto.message;

import com.triple_stack.route_in_backend.entity.RoomParticipant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRoomReqDto {
    private Integer userId;
    private Integer roomId;

    public RoomParticipant toEntity() {
        return RoomParticipant.builder()
                .userId(roomId)
                .roomId(roomId)
                .build();
    }
}
