package com.swpteam.smokingcessation.domain.dto.notification;

import com.swpteam.smokingcessation.domain.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    @NotBlank(message = "ACCOUNT_ID_REQUIRED")
    String accountId;

    @NotBlank(message = "NOTIFICATION_CONTENT_REQUIRED")
    String content;

    NotificationType notificationType;
}
