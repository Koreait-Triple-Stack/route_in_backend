package com.triple_stack.route_in_backend.dto.chat;

import com.triple_stack.route_in_backend.entity.RoomParticipant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoomFavoriteReqDto {
    private Integer userId;
    private Integer roomId;
    private Boolean favorite;

    public RoomParticipant toEntity() {
        return RoomParticipant.builder()
                .userId(userId)
                .roomId(roomId)
                .favorite(favorite)
                .build();
    }
}
