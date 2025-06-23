package com.swpteam.smokingcessation.domain.dto.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatResponse {
    String username;
    String content;
    LocalDateTime sentAt;
    boolean isFirstTime;
}
