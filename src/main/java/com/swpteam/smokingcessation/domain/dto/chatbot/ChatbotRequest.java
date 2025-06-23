package com.swpteam.smokingcessation.domain.dto.chatbot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatbotRequest {

    @NotBlank(message = "PROMPT_NOT_BLANK")
    @Size(max = 200, message = "PROMPT_MAX_SIZE")
    String prompt;
}
