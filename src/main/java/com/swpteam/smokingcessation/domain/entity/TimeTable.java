package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swpteam.smokingcessation.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeTable extends BaseEntity {

    String name;
    String description;
    LocalDateTime startedAt;
    LocalDateTime endedAt;

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    @JsonBackReference
    private Coach coach;
}
