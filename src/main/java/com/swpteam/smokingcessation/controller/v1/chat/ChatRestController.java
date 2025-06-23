package com.swpteam.smokingcessation.controller.v1.chat;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.chat.ChatRestResponse;
import com.swpteam.smokingcessation.service.interfaces.chat.IChatService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatRestController {
    IChatService chatService;

    @GetMapping
    ResponseEntity<ApiResponse<Page<ChatRestResponse>>> getChats(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<ChatRestResponse>>builder()
                        .code(SuccessCode.CHAT_GET_ALL.getCode())
                        .message(SuccessCode.CHAT_GET_ALL.getMessage())
                        .result(chatService.getChats(request))
                        .build());
    }

    @GetMapping("/{accountId}")
    ResponseEntity<ApiResponse<Page<ChatRestResponse>>> getChatsById(@PathVariable String accountId, @Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<ChatRestResponse>>builder()
                        .code(SuccessCode.CHAT_GET_BY_ID.getCode())
                        .message(SuccessCode.CHAT_GET_BY_ID.getMessage())
                        .result(chatService.getChatsById(accountId, request))
                        .build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Void>> deleteChat(@PathVariable String id) {
        chatService.deleteChat(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.CHAT_DELETED.getCode())
                        .message(SuccessCode.CHAT_DELETED.getMessage())
                        .build());
    }
}
