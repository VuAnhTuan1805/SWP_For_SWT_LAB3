package com.swpteam.smokingcessation.controller.v1.chat;

import com.swpteam.smokingcessation.domain.dto.chat.ChatRequest;
import com.swpteam.smokingcessation.domain.dto.chat.ChatResponse;
import com.swpteam.smokingcessation.service.interfaces.chat.IChatService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatController {
    IChatService chatService;

    @MessageMapping("/chat/send")
    @SendTo("/topic/public")
    public ChatResponse sendChatMessage(@Valid ChatRequest request) {
        return chatService.sendChatMessage(request);
    }
}
