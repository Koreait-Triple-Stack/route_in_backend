package com.triple_stack.route_in_backend.repository;

import com.triple_stack.route_in_backend.entity.Notification;
import com.triple_stack.route_in_backend.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationRepository {
    @Autowired
    private NotificationMapper notificationMapper;

    public int addNotification(Notification notification) {
        return notificationMapper.addNotification(notification);
    }

    public List<Notification> getNotificationListByUserId(Integer userId) {
        return notificationMapper.getNotificationListByUserId(userId);
    }

    public int deleteNotificationByNotificationId(Integer notificationId) {
        return notificationMapper.deleteNotificationByNotificationId(notificationId);
    }

    public int deleteNotificationByUserId(Integer userId) {
        return notificationMapper.deleteNotificationByUserId(userId);
    }

    public int markAllRead(Integer userId) {
        return notificationMapper.markAllRead(userId);
    }
}
