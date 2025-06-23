package com.swpteam.smokingcessation.domain.dto.blog;

import com.swpteam.smokingcessation.domain.enums.BlogStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogPostCreateRequest {

    @NotBlank(message = "BLOG_TITLE_REQUIRED")
    @Size(max = 255, message = "BLOG_TITLE_LIMIT")
    String title;

    @NotBlank(message = "BLOG_SLUG_REQUIRED")
    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "SLUG_PATTERN")
    @Size(max = 255, message = "SLUG_LENGTH_LIMIT")
    String slug;

    @Size(max = 1000, message = "COVER_IMAGE_LENGTH_LIMIT")
    @Pattern(
            regexp = "^(http|https)://.*$",
            message = "COVER_IMAGE_INVALID_URL"
    )
    String coverImageUrl;

    @Size(max = 500, message = "EXCERPT_LENGTH_LIMIT")
    String excerpt;

    String content;

    @NotNull(message = "BLOG_STATUS_REQUIRED")
    BlogStatus status;
}
