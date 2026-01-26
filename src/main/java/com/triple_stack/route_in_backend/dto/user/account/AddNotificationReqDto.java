package com.triple_stack.route_in_backend.dto.user.account;

import com.triple_stack.route_in_backend.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNotificationReqDto {
    private List<Integer> userIds;
    private String title;
    private String message;
    private String path;
    private String profileImg;

    public Notification toEntity(Integer userId) {
        return Notification.builder()
                .userId(userId)
                .message(message)
                .path(path)
                .profileImg(profileImg)
                .build();
    }
}
