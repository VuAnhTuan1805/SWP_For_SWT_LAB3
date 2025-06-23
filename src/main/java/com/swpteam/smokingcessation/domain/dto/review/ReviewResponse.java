package com.swpteam.smokingcessation.domain.dto.review;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {

    String id;
    String memberId;
    String coachId;
    String type;
    String comment;
    int rating;
    LocalDate createdAt;
    LocalDate updatedAt;
}
