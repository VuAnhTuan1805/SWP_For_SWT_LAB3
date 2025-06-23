package com.swpteam.smokingcessation.service.impl.notification;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.notification.MarkAsReadRequest;
import com.swpteam.smokingcessation.domain.dto.notification.NotificationRequest;
import com.swpteam.smokingcessation.domain.dto.notification.NotificationResponse;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Notification;
import com.swpteam.smokingcessation.domain.mapper.NotificationMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.NotificationRepository;
import com.swpteam.smokingcessation.service.interfaces.identity.IAccountService;
import com.swpteam.smokingcessation.service.interfaces.notification.INotificationService;
import com.swpteam.smokingcessation.utils.AuthUtil;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements INotificationService {

    SimpMessagingTemplate simpMessagingTemplate;
    NotificationMapper notificationMapper;
    NotificationRepository notificationRepository;

    IAccountService accountService;
    AuthUtil authUtil;

    @Override
    @Transactional
    @CachePut(value = "MESSAGE_CACHE", key = "#result.getId()")
    public void sendNotification(NotificationRequest request) {
        Notification notification = notificationMapper.toEntity(request);

        Account account = request.getAccountId() != null ?
                accountService.findAccountById(request.getAccountId())
                :
                null;

        notification.setAccount(account);
        notification.setSentAt(LocalDateTime.now());
        notification.setRead(false);

        NotificationResponse response = notificationMapper.toResponse(notificationRepository.save(notification));

        if (request.getAccountId() == null) {
            simpMessagingTemplate.convertAndSend("/topic/notifications/", response);
        } else {
            simpMessagingTemplate.convertAndSend("/topic/notifications/" + request.getAccountId(), response);
        }
    }

    @Override
    @Transactional
    public void markAsRead(MarkAsReadRequest request) {
        Notification notification = findNotificationById(request.getNotificationId());

        if (!notification.isRead()) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    @Cacheable(value = "MESSAGE_CACHE", key = "#id")
    private Notification findNotificationById(String id) {
        return notificationRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Page<NotificationResponse> getNotifications(PageableRequest request) {
        ValidationUtil.checkFieldExist(Notification.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Notification> notifications = notificationRepository.findAllByIsDeletedFalse(pageable);

        return notifications.map(notificationMapper::toResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Page<NotificationResponse> getNotificationsById(String id, PageableRequest request) {
        ValidationUtil.checkFieldExist(Notification.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Notification> notifications = notificationRepository.findByAccountIdAndIsDeletedFalse(id, pageable);

        return notifications.map(notificationMapper::toResponse);
    }

    @Override
    public void deleteNotification(String id) {
        Notification notification = notificationRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));

        boolean haveAccess = authUtil.isAdminOrOwner(notification.getAccount().getId());
        if (!haveAccess) {
            throw new AppException(ErrorCode.OTHERS_NOTIFICATION_CANNOT_BE_DELETED);
        }

        notification.setDeleted(true);
        notificationRepository.save(notification);
    }

    @Override
    public void deleteAllNotification(String id) {
        List<Notification> notifications = notificationRepository.findAllByAccountIdAndIsDeletedFalse(id);

        if (notifications.isEmpty()) {
            throw new AppException(ErrorCode.NOTIFICATION_NOT_FOUND);
        }
        boolean haveAccess = authUtil.isAdminOrOwner(notifications.getFirst().getAccount().getId());
        if (!haveAccess) {
            throw new AppException(ErrorCode.OTHERS_NOTIFICATION_CANNOT_BE_DELETED);
        }

        notifications.forEach(notification -> notification.setDeleted(true));
        notificationRepository.saveAll(notifications);
    }
}
