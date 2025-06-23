package com.swpteam.smokingcessation.domain.entity;

import com.swpteam.smokingcessation.common.BaseEntity;
import com.swpteam.smokingcessation.domain.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "accountId")
    Account account;

    @Enumerated(EnumType.STRING)
    NotificationType notificationType;

    String content;
    LocalDateTime sentAt;
    boolean isRead;
}
