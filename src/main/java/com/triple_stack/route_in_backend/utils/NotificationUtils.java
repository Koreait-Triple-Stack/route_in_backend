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
    private final SimpMessagingTemplate messagingTemplate;

    public void sendAndAddNotification(List<Notification> notifications) {
        for (Notification notification : notifications) {
            Map<String, Object> payload = Map.of(
                    "type", "NOTI",
                    "userId", notification.getUserId(),
                    "title", notification.getTitle(),
                    "message", notification.getMessage(),
                    "path", notification.getPath(),
                    "profileImg", notification.getProfileImg(),
                    "createDt", Instant.now().toString()
            );

            messagingTemplate.convertAndSendToUser(
                    String.valueOf(notification.getUserId()),
                    "/queue/notification",
                    payload
            );
        }
    }

    public void sendAndAddNotification(List<Notification> notifications, Integer roomId) {
        for (Notification notification : notifications) {
            Map<String, Object> payload = Map.of(
                    "type", "CHAT_MESSAGE",
                    "userId", notification.getUserId(),
                    "title", notification.getTitle(),
                    "message", notification.getMessage(),
                    "path", notification.getPath(),
                    "profileImg", notification.getProfileImg(),
                    "createDt", Instant.now().toString(),
                    "roomId", roomId
            );

            messagingTemplate.convertAndSendToUser(
                    String.valueOf(notification.getUserId()),
                    "/queue/notification",
                    payload
            );
        }
    }
}
