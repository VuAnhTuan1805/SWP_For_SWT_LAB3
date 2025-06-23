package com.swpteam.smokingcessation.controller.v1.notification;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.notification.NotificationResponse;
import com.swpteam.smokingcessation.service.interfaces.notification.INotificationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationRestController {
    INotificationService notificationService;

    @GetMapping
    ResponseEntity<ApiResponse<Page<NotificationResponse>>> getChats(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<NotificationResponse>>builder()
                        .code(SuccessCode.NOTIFICATION_GET_ALL.getCode())
                        .message(SuccessCode.NOTIFICATION_GET_ALL.getMessage())
                        .result(notificationService.getNotifications(request))
                        .build());
    }

    @GetMapping("/{accountId}")
    ResponseEntity<ApiResponse<Page<NotificationResponse>>> getChatsById(@PathVariable String accountId, @Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<NotificationResponse>>builder()
                        .code(SuccessCode.NOTIFICATION_GET_BY_ID.getCode())
                        .message(SuccessCode.NOTIFICATION_GET_BY_ID.getMessage())
                        .result(notificationService.getNotificationsById(accountId, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Void>> deleteChat(@PathVariable String id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.NOTIFICATION_DELETED.getCode())
                        .message(SuccessCode.NOTIFICATION_DELETED.getMessage())
                        .build());
    }

    @DeleteMapping("/all/{accountId}")
    public ResponseEntity<ApiResponse<Void>> deleteAllNotifications(@PathVariable String accountId) {
        notificationService.deleteAllNotification(accountId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.ALL_NOTIFICATION_DELETED.getCode())
                        .message(SuccessCode.ALL_NOTIFICATION_DELETED.getMessage())
                        .build()
        );
    }
}
