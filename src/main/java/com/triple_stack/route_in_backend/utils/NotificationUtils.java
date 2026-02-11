package com.triple_stack.route_in_backend.utils;

import com.triple_stack.route_in_backend.entity.Notification;
import com.triple_stack.route_in_backend.repository.NotificationRepository;
import com.triple_stack.route_in_backend.websocket.presence.PresenceStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationUtils {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PresenceStore presenceStore;

    @Transactional
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

            notificationRepository.addNotification(notification);

            messagingTemplate.convertAndSend(
                    "/topic/notification/" + notification.getUserId(),
                    payload
            );
        }
    }

    @Transactional
    public void sendAndAddNotification(List<Notification> notifications, Integer roomId) {
        for (Notification notification : notifications) {
            System.out.println("[NOTI] skip? user="
                + notification.getUserId()
                + " active="
                + presenceStore.isUserActiveInRoom(notification.getUserId(), roomId));

            System.out.println("[NOTI SEND] toUser=" + notification.getUserId()
                    + " roomId=" + roomId);

            if (presenceStore.isUserActiveInRoom(notification.getUserId(), roomId)) {
                continue;
            }

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

            notificationRepository.addNotification(notification);

            messagingTemplate.convertAndSend(
                    "/topic/notification/" + notification.getUserId(),
                    payload
            );
        }
    }

    @Transactional
    public void sendMuteNotification(Integer roomId, Integer userId) {
        Map<String, Object> payload = Map.of(
            "type", "CHAT_MUTE",
            "userId", userId,
            "createDt", Instant.now().toString(),
            "roomId", roomId
        );

        messagingTemplate.convertAndSend(
                "/topic/notification/" + userId,
                payload
        );
    }
}
