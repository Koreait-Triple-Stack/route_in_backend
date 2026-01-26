package com.triple_stack.route_in_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    private Integer roomId;
    private String type; // 1대1 또는 그룹
    private String lastMessage;
    private LocalDateTime lastMessageDt;
    private LocalDateTime createDt;

    List<RoomParticipant> participants;
}
