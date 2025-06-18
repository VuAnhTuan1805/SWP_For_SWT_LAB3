package com.swpteam.smokingcessation.domain.dto.TimeTable;

import com.swpteam.smokingcessation.domain.entity.Coach;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeTableRequest {
    String coachId;
    String name;
    String description;
    LocalDateTime startedAt;
    LocalDateTime endedAt;
}
