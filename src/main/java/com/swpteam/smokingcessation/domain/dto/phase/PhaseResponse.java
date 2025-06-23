package com.swpteam.smokingcessation.domain.dto.phase;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swpteam.smokingcessation.domain.enums.PhaseStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhaseResponse {
    int phase;
    LocalDate startDate;
    LocalDate endDate;
    String id;
    String planId;
    String phaseName;
    String description;
    Integer cigaretteBound;
    PhaseStatus phaseStatus;
    LocalDate createdAt;
    LocalDate updatedAt;
}