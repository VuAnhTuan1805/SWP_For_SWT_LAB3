package com.swpteam.smokingcessation.domain.dto.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarkAsReadRequest {
    @NotBlank(message = "NOTIFICATION_ID_REQUIRED")
    String notificationId;
}
