package com.swpteam.smokingcessation.domain.dto.plan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swpteam.smokingcessation.domain.dto.phase.PhaseResponse;
import com.swpteam.smokingcessation.domain.enums.PlanStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanResponse {

    String id;
    String accountId;
    String planName;
    String description;
    Double successRate;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate createdAt;
    LocalDate updatedAt;
    PlanStatus planStatus;

    List<PhaseResponse> phases;
}
