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
public class RoomParticipant {
    private Integer roomId;
    private Integer userId;
    private String title;
    private String role;
    private LocalDateTime joinedDt;
    private LocalDateTime leftDt;
    private Integer profileUserId;
    private Boolean favorite;
    private Boolean muteNotification;

    private String profileImg;
    private String username;
}
