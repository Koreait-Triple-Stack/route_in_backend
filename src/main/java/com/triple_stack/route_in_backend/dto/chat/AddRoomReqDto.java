package com.triple_stack.route_in_backend.dto.chat;

import com.triple_stack.route_in_backend.entity.RoomParticipant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoomReqDto {
    private String type;
    private String title;
    private List<Integer> userIds;

    public RoomParticipant toEntity(Integer roomId, Integer userId, String role) {
        return RoomParticipant.builder()
                .roomId(roomId)
                .userId(userId)
                .title(title)
                .role(role)
                .build();
    }
}
