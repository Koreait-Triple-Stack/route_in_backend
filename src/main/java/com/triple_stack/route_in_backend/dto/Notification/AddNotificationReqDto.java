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
    private List<Integer> userIds;
    private String title;
    private String message;
    private String path;
    private String profileImg;
    // 웹소켓 이용해서 알림 보내는 거 수정해야함 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public Notification toEntity(Integer userId) {
        return Notification.builder()
                .userId(userId)
                .title(title)
                .message(message)
                .path(path)
                .profileImg(profileImg)
                .build();
    }
}
