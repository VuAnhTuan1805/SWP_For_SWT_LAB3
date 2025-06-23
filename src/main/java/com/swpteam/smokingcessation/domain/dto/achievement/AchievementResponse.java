package com.swpteam.smokingcessation.domain.dto.achievement;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AchievementResponse {

    String id;
    String name;
    String description;
    String iconUrl;
    String criteriaType;
    int criteriaValue;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
