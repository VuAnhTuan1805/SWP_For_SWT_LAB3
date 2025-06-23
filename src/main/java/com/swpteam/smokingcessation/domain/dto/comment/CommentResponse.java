package com.swpteam.smokingcessation.domain.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {

    String id;
    String content;
    int level;

    String blogId;
    String username;
    String parentCommentId;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    List<CommentResponse> commentChild;
}
