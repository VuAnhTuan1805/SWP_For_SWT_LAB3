package com.swpteam.smokingcessation.domain.dto.streak;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StreakRequest {
    @Min(value = 0, message = "STREAK_INVALID")
    @NotNull(message = "STREAK_REQUIRED")
    Integer streak;
}
