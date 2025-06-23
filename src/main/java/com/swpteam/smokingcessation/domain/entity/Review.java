package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swpteam.smokingcessation.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false, updatable = false)
    @JsonBackReference
    Account member;

    @ManyToOne
    @JoinColumn(name = "coachId", nullable = false)
    @JsonBackReference
    Account coach;

    String comment;

    int rating;
}