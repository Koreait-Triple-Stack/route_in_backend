package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private Integer messageId;
    private Integer roomId;
    private Integer senderId;
    private String type;
    private String content;
    private LocalDateTime createDt;
}
