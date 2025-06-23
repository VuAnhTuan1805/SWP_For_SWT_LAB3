package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swpteam.smokingcessation.domain.enums.PhaseStatus;
import com.swpteam.smokingcessation.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Phase extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "planId", nullable = false, updatable = false)
    Plan plan;

    String phaseName;
    String description;
    int cigaretteBound;
    LocalDate startDate;
    LocalDate endDate;

    @Enumerated(EnumType.STRING)
    PhaseStatus phaseStatus;
}
