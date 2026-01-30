package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.Notification.AddNotificationReqDto;
import com.triple_stack.route_in_backend.entity.Notification;
import com.triple_stack.route_in_backend.repository.NotificationRepository;
import com.triple_stack.route_in_backend.utils.NotificationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationUtils notificationUtils;

    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ApiRespDto<?> addNotification(AddNotificationReqDto addNotificationReqDto) {
            int result = notificationRepository.addNotification(addNotificationReqDto.toEntity());
            if (result != 1) {
                throw new RuntimeException("알림 전송 실패");
            }

            Map<String, Object> payload = Map.of(
                    "type", "NOTIFICATION",
                    "userId", addNotificationReqDto.getUserId(),
                    "title", addNotificationReqDto.getTitle(),
                    "message", addNotificationReqDto.getMessage(),
                    "path", addNotificationReqDto.getPath(),
                    "profileImg", addNotificationReqDto.getProfileImg(),
                    "createDt", Instant.now().toString()
            );

            messagingTemplate.convertAndSendToUser(
                    String.valueOf(addNotificationReqDto.getUserId()),
                    "/queue/notification",
                    payload
            );

        return new ApiRespDto<>("success", "알림 전송을 완료했습니다.", null);
    }

    @Transactional
    public ApiRespDto<?> getNotificationListByUserId(Integer userId) {
        notificationRepository.markAllRead(userId);
        return new ApiRespDto<>("success", "알림 조회 완료", notificationRepository.getNotificationListByUserId(userId));
    }

    public ApiRespDto<?> deleteNotificationByNotificationId(Integer notificationId) {
        int result = notificationRepository.deleteNotificationByNotificationId(notificationId);
        if (result != 1) {
            throw new RuntimeException("알림 삭제에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "알림 삭제를 완료했습니다", null);
    }

    public ApiRespDto<?> deleteNotificationByUserId(Integer userId) {
        int result = notificationRepository.deleteNotificationByUserId(userId);
        if (result == 0) {
            throw new RuntimeException("알림 전체 삭제에 실패했습니다.");
        }

        return new ApiRespDto<>("success", "알림 전체 삭제를 완료했습니다", null);
    }

    public ApiRespDto<?> countUnreadNotificationByUserId(Integer userId) {
        return new ApiRespDto<>("success", "읽지 않은 알림 갯수 조회를 완료했습니다.",
                notificationRepository.countUnreadNotificationByUserId(userId));
    }
}
