package com.swpteam.smokingcessation.domain.dto.booking;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {

    @NotBlank(message = "ACCOUNT_ID_REQUIRED")
    String accountId;

    @NotBlank(message = "COACH_ID_REQUIRED")
    String coachId;

    @NotNull(message = "STARTED_AT_REQUIRED")
    @FutureOrPresent(message = "STARTED_AT_MUST_BE_TODAY_OR_FUTURE")
    LocalDateTime startedAt;

    @NotNull(message = "ENDED_AT_REQUIRED")
    @Future(message = "ENDED_AT_MUST_BE_IN_FUTURE")
    LocalDateTime endedAt;

    String accessToken; // access token Google để tạo Google Meet
    String meetLink;

}