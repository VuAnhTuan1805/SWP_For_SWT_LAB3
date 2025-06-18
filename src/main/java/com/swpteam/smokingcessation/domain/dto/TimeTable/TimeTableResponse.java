package com.swpteam.smokingcessation.domain.dto.TimeTable;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeTableResponse {
    String id;
    String coachId;
    String description;
    LocalDateTime startedAt;
    LocalDateTime endedAt;
}
