package com.swpteam.smokingcessation.domain.dto.feedback;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackResponse {

    String id;
    String accountId;
    String comment;
    int rating;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
