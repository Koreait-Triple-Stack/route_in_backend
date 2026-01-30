package com.triple_stack.route_in_backend.dto.Notification;

import com.triple_stack.route_in_backend.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNotificationReqDto {
    private Integer userId;
    private String title;
    private String message;
    private String path;
    private String profileImg;

    public Notification toEntity() {
        return Notification.builder()
                .userId(userId)
                .title(title)
                .message(message)
                .path(path)
                .profileImg(profileImg)
                .build();
    }
}
