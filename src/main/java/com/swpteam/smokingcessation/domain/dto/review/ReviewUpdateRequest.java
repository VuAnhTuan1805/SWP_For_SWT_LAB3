package com.swpteam.smokingcessation.domain.dto.review;

import jakarta.validation.constraints.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewUpdateRequest {

    String comment;

    @Min(value = 1, message = "REVIEW_RATING_MIN")
    @Max(value = 5, message = "REVIEW_RATING_MAX")
    int rating;
}
