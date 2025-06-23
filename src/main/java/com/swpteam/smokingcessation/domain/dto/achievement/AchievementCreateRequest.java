package com.swpteam.smokingcessation.domain.dto.achievement;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AchievementCreateRequest {

    @NotBlank(message = "NAME_REQUIRED")
    String name;

    @NotBlank(message = "DESCRIPTION_REQUIRED")
    String description;

    @NotBlank(message = "ICON_URL_REQUIRED")
    String iconUrl;

    @NotBlank(message = "CRITERIA_TYPE_REQUIRED")
    String criteriaType;

    @NotNull(message = "CRITERIA_VALUE_REQUIRED")
    @Min(value = 0, message = "CRITERIA_VALUE_INVALID")
    Integer criteriaValue;
}
