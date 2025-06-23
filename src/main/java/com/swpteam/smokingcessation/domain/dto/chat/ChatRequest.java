package com.swpteam.smokingcessation.domain.dto.chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRequest {
    @NotBlank(message = "ACCOUNT_ID_REQUIRED")
    String accountId;

    @NotBlank(message = "CHAT_MESSAGE_REQUIRED")
    @Size(max = 50, message = "CHAT_SIZE_EXCEED")
    String content;
}
