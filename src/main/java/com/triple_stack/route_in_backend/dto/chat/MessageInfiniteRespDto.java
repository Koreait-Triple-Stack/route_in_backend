package com.triple_stack.route_in_backend.dto.chat;

import com.triple_stack.route_in_backend.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageInfiniteRespDto {
    private List<Message> messageList;
    private boolean hasNext;
    private Integer nextCursorMessageId;
    private LocalDateTime nextCursorCreateDt;
}
