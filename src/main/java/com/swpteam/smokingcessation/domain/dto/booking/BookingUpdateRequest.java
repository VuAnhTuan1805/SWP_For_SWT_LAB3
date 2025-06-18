package com.swpteam.smokingcessation.domain.dto.booking;

import com.swpteam.smokingcessation.domain.enums.BookingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingUpdateRequest {

    @NotNull(message = "BOOKING_STATUS_REQUIRED")
    BookingStatus status;
}
