package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.user.account.AddNotificationReqDto;
import com.triple_stack.route_in_backend.dto.user.account.NotificationItemDto;
import com.triple_stack.route_in_backend.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    private final SimpMessagingTemplate messagingTemplate;


    @Transactional
    public ApiRespDto<?> addNotification(AddNotificationReqDto addNotificationReqDto) {
        Map<String, Object> payload = addNotificationReqDto.toSendTemplate();

        for (Integer userId : addNotificationReqDto.getUserIds()) {
            int result = notificationRepository.addNotification(addNotificationReqDto.toEntity(userId));
            if (result != 1) {
                throw new RuntimeException("알림 전송에 실패했습니다.");
            }

            messagingTemplate.convertAndSendToUser(
                    String.valueOf(userId),
                    "/queue/notification",
                    payload
            );
        }

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
}
