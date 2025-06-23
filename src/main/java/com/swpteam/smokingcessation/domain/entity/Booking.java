package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swpteam.smokingcessation.common.BaseEntity;
import com.swpteam.smokingcessation.domain.enums.BookingStatus;
import jakarta.persistence.*;
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
public class Booking extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false, updatable = false)
    Account account;

    @ManyToOne
    @JoinColumn(name = "coachId", nullable = false)
    Coach coach;

    String meetLink;
    LocalDateTime startedAt;
    LocalDateTime endedAt;

    @Enumerated(EnumType.STRING)
    BookingStatus status;
}