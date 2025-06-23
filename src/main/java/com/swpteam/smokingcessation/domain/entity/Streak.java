package com.swpteam.smokingcessation.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Streak {
    @Id
    String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "memberId", nullable = false)
    Member member;

    int streak;
}
