package com.swpteam.smokingcessation.domain.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {

    @NotBlank(message = "CATEGORY_NAME_REQUIRED")
    @Size(max = 100, message = "CATEGORY_MAX_LENGTH")
    String name;
}
