package com.swpteam.smokingcessation.controller.v1.notification;


import com.swpteam.smokingcessation.domain.dto.notification.MarkAsReadRequest;
import com.swpteam.smokingcessation.domain.dto.notification.NotificationRequest;
import com.swpteam.smokingcessation.service.interfaces.notification.INotificationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    INotificationService notificationService;

    // /topic/notify
    @MessageMapping("/notifications/send")
    public void sendNotification(@Valid @Payload NotificationRequest request) {
        notificationService.sendNotification(request);
    }

    // /app/mark-as-read
    @MessageMapping("/notifications/mark-as-read")
    public void markAsRead(@Valid @Payload MarkAsReadRequest request) {
        notificationService.markAsRead(request);
    }
}
