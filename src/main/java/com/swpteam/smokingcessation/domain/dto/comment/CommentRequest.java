package com.swpteam.smokingcessation.domain.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class CommentRequest {

    @NotBlank(message = "COMMENT_CONTENT_REQUIRED")
    String content;

    @PositiveOrZero(message = "LEVEL_POSITIVE")
    int level;

    @NotBlank(message = "COMMENT_BLOG_REQUIRED")
    String blogId;

    @NotBlank(message = "COMMENT_USER_REQUIRED")
    String userId;

    String parentCommentId;
}
