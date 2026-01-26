package com.triple_stack.route_in_backend.utils;

import com.triple_stack.route_in_backend.entity.Notification;
import com.triple_stack.route_in_backend.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationUtils {
    @Autowired
    private NotificationRepository notificationRepository;

    private final SimpMessagingTemplate messagingTemplate;

    public void sendAndAddNotification(List<Integer> userIds, String title, String message, String path, String profileImg) {
        Map<String, Object> payload = Map.of(
                "type", "NOTIFICATION",
                "title", title,
                "message", message,
                "path", path,
                "profileImg", profileImg,
                "createDt", Instant.now().toString()
        );

        for (Integer userId : userIds) {
            int result = notificationRepository.addNotification(toEntity(userId, title, message, path, profileImg));
            if (result != 1) {
                throw new RuntimeException("알림 전송에 실패했습니다.");
            }

            messagingTemplate.convertAndSendToUser(
                    String.valueOf(userId),
                    "/queue/notification",
                    payload
            );
        }
    }

    private Notification toEntity(Integer userId, String title, String message, String path, String profileImg) {
        return Notification.builder()
                .userId(userId)
                .title(title)
                .message(message)
                .path(path)
                .profileImg(profileImg)
                .build();
    }
}
