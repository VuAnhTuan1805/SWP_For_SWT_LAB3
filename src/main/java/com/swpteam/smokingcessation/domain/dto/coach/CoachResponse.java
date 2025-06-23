package com.swpteam.smokingcessation.domain.dto.coach;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoachResponse {

    String accountId;
    String id;
    String fullName;
    String bio;
    int experienceYears;
    String socialLinks;
    String specializations;
    String certificates;
    LocalDate createdAt;
    LocalDate updatedAt;
}