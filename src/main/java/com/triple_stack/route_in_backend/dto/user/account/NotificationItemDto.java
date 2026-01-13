package com.triple_stack.route_in_backend.dto.user.account;

import com.triple_stack.route_in_backend.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationItemDto {
    private Integer userId;
    private String message;
    private String path;

    public Notification toEntity() {
        return Notification.builder()
                .userId(userId)
                .message(message)
                .path(path)
                .build();
    }
}
