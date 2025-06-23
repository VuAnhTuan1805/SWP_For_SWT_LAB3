package com.swpteam.smokingcessation.service.impl.notification;

import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.notification.MarkAsReadRequest;
import com.swpteam.smokingcessation.domain.entity.Notification;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    NotificationRepository notificationRepository;

    @InjectMocks
    NotificationServiceImpl notificationService;

    String notificationId;
    Notification notification;

    @BeforeEach
    void setUp() {
        notificationId = "31c14ab3-f8ee-482f-90f6-6bff56d2d77c";
        notification = new Notification();
        notification.setId(notificationId);
        notification.setRead(false);
    }

    @Test
    void markAsRead_shouldMarkNotificationAsRead() {
        // Given
        MarkAsReadRequest request = MarkAsReadRequest.builder()
                .notificationId(notificationId)
                .build();

        when(notificationRepository.findByIdAndIsDeletedFalse(notificationId))
                .thenReturn(Optional.of(notification));
        when(notificationRepository.save(any(Notification.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        notificationService.markAsRead(request);

        // Then
        assertTrue(notification.isRead());
        verify(notificationRepository).save(notification);
    }

    @Test
    void markAsRead_shouldThrowException_whenNotificationNotFound() {
        // Given
        MarkAsReadRequest request = MarkAsReadRequest.builder()
                .notificationId(notificationId)
                .build();

        when(notificationRepository.findByIdAndIsDeletedFalse(notificationId))
                .thenReturn(Optional.empty());

        // Then
        AppException ex = assertThrows(AppException.class, () -> {
            notificationService.markAsRead(request);
        });

        assertEquals(ErrorCode.NOTIFICATION_NOT_FOUND, ex.getErrorCode());
        verify(notificationRepository, never()).save(any());
    }

    @Test
    void markAsRead_shouldNotSave_whenAlreadyRead() {
        // Given
        notification.setRead(true);
        MarkAsReadRequest request = MarkAsReadRequest.builder()
                .notificationId(notificationId)
                .build();

        when(notificationRepository.findByIdAndIsDeletedFalse(notificationId))
                .thenReturn(Optional.of(notification));

        // When
        notificationService.markAsRead(request);

        // Then
        verify(notificationRepository, never()).save(any());
    }
}
