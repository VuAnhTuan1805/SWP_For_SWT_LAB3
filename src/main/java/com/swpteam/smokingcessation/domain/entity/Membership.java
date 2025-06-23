package com.swpteam.smokingcessation.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.swpteam.smokingcessation.domain.enums.Currency;
import com.swpteam.smokingcessation.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Membership extends BaseEntity {

    @Builder.Default
    @OneToMany(mappedBy = "membership")
    @JsonIgnore
    List<Subscription> subscriptions = new ArrayList<>();

    String name;
    int durationDays;
    double price;
    String description;

    @Enumerated(EnumType.STRING)
    Currency currency;
}
