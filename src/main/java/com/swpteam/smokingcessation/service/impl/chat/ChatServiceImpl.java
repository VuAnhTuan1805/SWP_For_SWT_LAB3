package com.swpteam.smokingcessation.service.impl.chat;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.chat.ChatRequest;
import com.swpteam.smokingcessation.domain.dto.chat.ChatResponse;
import com.swpteam.smokingcessation.domain.dto.chat.ChatRestResponse;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Chat;
import com.swpteam.smokingcessation.domain.mapper.ChatMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.AccountRepository;
import com.swpteam.smokingcessation.repository.ChatRepository;
import com.swpteam.smokingcessation.service.interfaces.chat.IChatService;
import com.swpteam.smokingcessation.utils.AuthUtil;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ChatServiceImpl implements IChatService {

    AccountRepository accountRepository;
    ChatRepository chatRepository;
    ChatMapper chatMapper;
    AuthUtil authUtil;

    @Override
    public ChatResponse sendChatMessage(ChatRequest request) {
        Account account = accountRepository.findByIdAndIsDeletedFalse(request.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        boolean isFirstTime = !chatRepository.existsByAccountIdAndIsDeletedFalse(request.getAccountId());

        Chat chat = Chat.builder()
                .account(account)
                .content(request.getContent())
                .build();

        chatRepository.save(chat);

        ChatResponse chatResponse = chatMapper.toResponse(chat);
        chatResponse.setFirstTime(isFirstTime);
        return chatResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Page<ChatRestResponse> getChats(PageableRequest request) {
        ValidationUtil.checkFieldExist(Chat.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Chat> chats = chatRepository.findAllByIsDeletedFalse(pageable);

        return chats.map(chatMapper::toRestResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Page<ChatRestResponse> getChatsById(String id, PageableRequest request) {
        ValidationUtil.checkFieldExist(Chat.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Chat> chats = chatRepository.findByAccountIdAndIsDeletedFalse(id, pageable);

        return chats.map(chatMapper::toRestResponse);
    }

    @Override
    public void deleteChat(String id) {
        Chat chat = chatRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new AppException(ErrorCode.CHAT_NOT_FOUND));

        boolean haveAccess = authUtil.isAdminOrOwner(chat.getAccount().getId());
        if (!haveAccess) {
            throw new AppException(ErrorCode.OTHERS_CHAT_CANNOT_BE_DELETED);
        }

        chat.setDeleted(true);
        chatRepository.save(chat);
    }

}
