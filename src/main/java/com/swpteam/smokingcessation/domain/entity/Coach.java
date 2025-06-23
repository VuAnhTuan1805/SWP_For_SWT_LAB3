package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swpteam.smokingcessation.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coach extends BaseEntity {

    @MapsId
    @OneToOne
    @JoinColumn(name = "accountId", nullable = false, updatable = false)
    Account account;

    @Builder.Default
    @OneToMany(mappedBy = "coach")
    List<Booking> bookings = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TimeTable> timetables = new ArrayList<>();

    String fullName;
    String bio;
    int experienceYears;
    String socialLinks;
    String specializations;
    String certificates;
}
