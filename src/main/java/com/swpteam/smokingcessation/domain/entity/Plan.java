package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swpteam.smokingcessation.domain.enums.PlanStatus;
import com.swpteam.smokingcessation.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Plan extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false, updatable = false)
    Account account;

    @Builder.Default
    @OneToMany(mappedBy = "plan")
    @JsonIgnore
    List<Phase> phases = new ArrayList<>();

    String planName;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    double successRate;

    @Enumerated(EnumType.STRING)
    PlanStatus planStatus;
}
