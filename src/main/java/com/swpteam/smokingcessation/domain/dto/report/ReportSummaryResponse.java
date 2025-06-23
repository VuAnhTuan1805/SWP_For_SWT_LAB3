package com.swpteam.smokingcessation.domain.dto.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportSummaryResponse {
    double revenue;
    int newAccounts;
    int currentAccounts;
    int activeAccounts;
    LocalDateTime fromDate;
    LocalDateTime toDate;
}
