package com.swpteam.smokingcessation.service.interfaces.notification;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.notification.MarkAsReadRequest;
import com.swpteam.smokingcessation.domain.dto.notification.NotificationRequest;
import com.swpteam.smokingcessation.domain.dto.notification.NotificationResponse;
import org.springframework.data.domain.Page;

public interface INotificationService {
    void sendNotification(NotificationRequest request);

    void markAsRead(MarkAsReadRequest request);

    Page<NotificationResponse> getNotifications(PageableRequest request);

    Page<NotificationResponse> getNotificationsById(String id, PageableRequest request);

    void deleteNotification(String id);

    void deleteAllNotification(String id);
}
