package com.triple_stack.route_in_backend.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRespDto {
    private Integer roomId;
    private String type;
    private LocalDateTime createDt;
    private String lastMessage;
    private LocalDateTime lastMessageDt;
    private Integer lastMessageId;

    private Integer userId;
    private String title;
    private String role;
    private LocalDateTime joinedDt;
    private LocalDateTime leftDt;
    private Boolean favorite;
    private Boolean muteNotification;

    private Integer unreadCnt;

    private String profileImg;
}
