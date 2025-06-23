package com.swpteam.smokingcessation.domain.dto.booking;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponse {

    String id;
    String accountId;
    String coachId;
    String meetLink;
    LocalDateTime startedAt;
    LocalDateTime endedAt;
    boolean isApproved;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}