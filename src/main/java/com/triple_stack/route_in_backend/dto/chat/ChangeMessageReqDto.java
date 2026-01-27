package com.triple_stack.route_in_backend.dto.chat;

import com.triple_stack.route_in_backend.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeMessageReqDto {
    private Integer messageId;
    private Integer senderId;
    private String content;

    public Message toEntity() {
        return Message.builder()
                .messageId(messageId)
                .senderId(senderId)
                .content(content)
                .build();
    }
}
