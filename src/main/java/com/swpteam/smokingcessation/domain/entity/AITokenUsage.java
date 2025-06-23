package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swpteam.smokingcessation.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AITokenUsage extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false, updatable = false)
    Account account;

    @Column(nullable = false, unique = true)
    LocalDate date;

    int tokensUsed;
}
