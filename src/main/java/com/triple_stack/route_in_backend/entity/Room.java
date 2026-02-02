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
    private String type; // 1대1(DM) 또는 그룹(GROUP)
    private Integer unreadCnt;
    private LocalDateTime createDt;
    private String lastMessage;
    private LocalDateTime lastMessageDt;
    private Integer lastMessageId;

    List<RoomParticipant> participants;
}
