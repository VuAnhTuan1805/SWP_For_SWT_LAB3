package com.swpteam.smokingcessation.domain.entity;

import com.swpteam.smokingcessation.common.BaseEntity;
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
public class Achievement extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    private String name;

    private String iconUrl;

    private String description;

    private String criteriaType;

    private int criteriaValue;
}
