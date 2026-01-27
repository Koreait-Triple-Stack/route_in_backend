package com.triple_stack.route_in_backend.dto.chat;

import com.triple_stack.route_in_backend.entity.Message;
import com.triple_stack.route_in_backend.entity.Room;
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

    public Room toRoomEntity() {
        return Room.builder()
                .roomId(roomId)
                .lastMessage(content)
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
