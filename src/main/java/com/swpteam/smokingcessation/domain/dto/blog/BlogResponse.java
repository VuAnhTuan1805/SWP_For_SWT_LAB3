package com.swpteam.smokingcessation.domain.dto.blog;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.swpteam.smokingcessation.domain.enums.BlogStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogResponse {

    String id;
    String authorName;
    String categoryName;
    String title;
    String slug;
    String coverImageUrl;
    String excerpt;
    String content;
    BlogStatus status;

    String createdBy;
    LocalDateTime createdAt;

    String updateBy;
    LocalDateTime updatedAt;
}
