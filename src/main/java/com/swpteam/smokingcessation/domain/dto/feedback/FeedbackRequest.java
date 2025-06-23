package com.swpteam.smokingcessation.domain.dto.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackRequest {

    String comment;

    @Min(value = 1, message = "FEEDBACK_RATING_MIN")
    @Max(value = 5, message = "FEEDBACK_RATING_MAX")
    Integer rating;
}
