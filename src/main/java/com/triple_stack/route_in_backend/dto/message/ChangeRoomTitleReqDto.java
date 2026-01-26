package com.triple_stack.route_in_backend.dto.message;

import com.triple_stack.route_in_backend.entity.RoomParticipant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoomTitleReqDto {
    private Integer roomId;
    private Integer userId;
    private String title;

    public RoomParticipant toEntity() {
        return RoomParticipant.builder()
                .roomId(roomId)
                .userId(userId)
                .title(title)
                .build();
    }
}
