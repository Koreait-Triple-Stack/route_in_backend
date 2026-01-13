package com.triple_stack.route_in_backend.mapper;

import com.triple_stack.route_in_backend.entity.Notification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {
    int addNotification(Notification notification);
    List<Notification> getNotificationListByUserId(Integer userId);
    int deleteNotificationByNotificationId(Integer notificationId);
    int deleteNotificationByUserId(Integer userId);
}
