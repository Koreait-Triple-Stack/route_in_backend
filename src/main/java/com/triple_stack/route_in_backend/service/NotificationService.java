package com.triple_stack.route_in_backend.service;

import com.triple_stack.route_in_backend.dto.ApiRespDto;
import com.triple_stack.route_in_backend.dto.user.account.AddNotificationReqDto;
import com.triple_stack.route_in_backend.dto.user.account.NotificationItemDto;
import com.triple_stack.route_in_backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public ApiRespDto<?> addNotification(AddNotificationReqDto addNotificationReqDto) {
        for (NotificationItemDto notification : addNotificationReqDto.getNotifications()) {
            int result = notificationRepository.addNotification(notification.toEntity());
            if (result != 1) {
                throw new RuntimeException("알림 전송에 실패했습니다.");
            }
        }

        return new ApiRespDto<>("success", "알림 전송을 완료했습니다.", null);
    }

    public ApiRespDto<?> getNotificationListByUserId(Integer userId) {
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
