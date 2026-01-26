package com.triple_stack.route_in_backend.dto.message;

import com.triple_stack.route_in_backend.entity.Message;
import com.triple_stack.route_in_backend.entity.RoomParticipant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMessageReqDto {
    private Integer roomId;
    private Integer senderId;
    private String type;
    private String content;

    public RoomParticipant toRoomEntity() {
        return RoomParticipant.builder()
                .roomId(roomId)
                .userId(senderId)
                .build();
    }

    public Message toMessageEntity() {
        return Message.builder()
                .roomId(roomId)
                .senderId(senderId)
                .type(type)
                .content(content)
                .build();
    }
}
