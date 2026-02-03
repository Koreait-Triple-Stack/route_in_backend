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
public class Notification {
    private Integer notificationId;
    private Integer userId;
    private String title;
    private String message;
    private String path;
    private String profileImg;
    private Boolean isRead;
    private LocalDateTime createDt;

    private Boolean muteNotification;
}
