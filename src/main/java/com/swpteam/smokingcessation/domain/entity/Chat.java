package com.swpteam.smokingcessation.domain.entity;

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
public class Chat extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "accountId")
    Account account;

    String content;
}
