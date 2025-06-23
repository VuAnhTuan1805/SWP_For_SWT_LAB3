package com.swpteam.smokingcessation.domain.dto.report;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportSummaryRequest {
    @Past(message = "PAST_FROM_DATE")
    @NotNull(message = "FROM_DATE_REQUIRED")
    LocalDateTime from;

    @Past(message = "PAST_TO_DATE")
    LocalDateTime to;
}
