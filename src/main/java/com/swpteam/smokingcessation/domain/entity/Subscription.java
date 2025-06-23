package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swpteam.smokingcessation.common.BaseEntity;
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
public class Subscription extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false, updatable = false)
    Account account;

    @ManyToOne
    @JoinColumn(name = "membershipId", nullable = false)
    Membership membership;

    LocalDate startDate;
    LocalDate endDate;

    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return (startDate == null || !today.isBefore(startDate)) &&
                (endDate == null || !today.isAfter(endDate));
    }
}
