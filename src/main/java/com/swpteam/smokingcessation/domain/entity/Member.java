package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.swpteam.smokingcessation.domain.enums.MemberGender;
import com.swpteam.smokingcessation.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Member extends BaseEntity {

    @MapsId
    @OneToOne
    @JoinColumn(name = "accountId", nullable = false, updatable = false)
    Account account;

    String fullName;
    LocalDate dob;
    String address;
    int score;
    int highestStreak;
    LocalDateTime lastCounterReset;

    @Enumerated(EnumType.STRING)
    MemberGender gender;
}
