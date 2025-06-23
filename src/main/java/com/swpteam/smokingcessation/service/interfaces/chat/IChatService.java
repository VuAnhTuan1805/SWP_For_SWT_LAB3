package com.swpteam.smokingcessation.service.interfaces.chat;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.chat.ChatRequest;
import com.swpteam.smokingcessation.domain.dto.chat.ChatResponse;
import com.swpteam.smokingcessation.domain.dto.chat.ChatRestResponse;
import org.springframework.data.domain.Page;

public interface IChatService {
    ChatResponse sendChatMessage(ChatRequest request);

    Page<ChatRestResponse> getChats(PageableRequest request);

    Page<ChatRestResponse> getChatsById(String id, PageableRequest request);

    void deleteChat(String id);
}
