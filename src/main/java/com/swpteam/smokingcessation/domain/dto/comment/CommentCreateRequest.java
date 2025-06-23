package com.swpteam.smokingcessation.domain.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentCreateRequest {

    @NotBlank(message = "COMMENT_CONTENT_REQUIRED")
    String content;

    @NotBlank(message = "COMMENT_BLOG_REQUIRED")
    String blogId;
}
