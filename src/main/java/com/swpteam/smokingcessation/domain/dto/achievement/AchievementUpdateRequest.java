package com.swpteam.smokingcessation.domain.dto.achievement;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AchievementUpdateRequest {

    String name;

    String description;

    String iconUrl;

    String criteriaType;

    @Min(value = 0, message = "CRITERIA_VALUE_INVALID")
    int criteriaValue;
}
