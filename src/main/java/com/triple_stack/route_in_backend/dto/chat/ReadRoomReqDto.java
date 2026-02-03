package com.triple_stack.route_in_backend.dto.chat;

import com.triple_stack.route_in_backend.entity.RoomRead;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadRoomReqDto {
    private Integer roomId;
    private Integer userId;
    private Integer lastMessageId;

    public RoomRead toEntity() {
        return RoomRead.builder()
                .roomId(roomId)
                .userId(userId)
                .lastReadMessageId(lastMessageId)
                .build();
    }
}
