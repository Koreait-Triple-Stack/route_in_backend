package com.triple_stack.route_in_backend.controller;

import com.triple_stack.route_in_backend.dto.user.account.AddNotificationReqDto;
import com.triple_stack.route_in_backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/add")
    public ResponseEntity<?> addNotification(@RequestBody AddNotificationReqDto addNotificationReqDto) {
        return ResponseEntity.ok(notificationService.addNotification(addNotificationReqDto));
    }

    @GetMapping("/get/list/{userId}")
    public ResponseEntity<?> getNotificationListByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(notificationService.getNotificationListByUserId(userId));
    }

    @PostMapping("/delete/{notificationId}")
    public ResponseEntity<?> deleteNotificationByNotificationId(@PathVariable Integer notificationId) {
        return ResponseEntity.ok(notificationService.deleteNotificationByNotificationId(notificationId));
    }

    @PostMapping("/delete/all/{userId}")
    public ResponseEntity<?> deleteNotificationByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(notificationService.deleteNotificationByUserId(userId));
    }
    
    @GetMapping("/count/{userId}")
    public ResponseEntity<?> dcountUnreadNotificationByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(notificationService.countUnreadNotificationByUserId(userId));
    }
}
