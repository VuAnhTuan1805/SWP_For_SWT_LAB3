package com.swpteam.smokingcessation.service.impl.chatbot;

import com.swpteam.smokingcessation.constant.AIToken;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.chatbot.ChatbotRequest;
import com.swpteam.smokingcessation.domain.dto.chatbot.ChatbotResponse;
import com.swpteam.smokingcessation.domain.entity.AITokenUsage;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.enums.Role;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.integration.AI.IAIService;
import com.swpteam.smokingcessation.repository.AITokenUsageRepository;
import com.swpteam.smokingcessation.service.interfaces.chatbot.IChatbotService;
import com.swpteam.smokingcessation.utils.AuthUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatbotServiceImpl implements IChatbotService {

    AITokenUsageRepository aiTokenUsageRepository;
    AuthUtil authUtil;
    IAIService aiService;

    @Override
    @Transactional
    public ChatbotResponse chat(ChatbotRequest request) {
        Account account = authUtil.getCurrentAccount()
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        AITokenUsage usage = aiTokenUsageRepository.findByAccountIdAndDate(account.getId(), LocalDate.now())
                .orElse(new AITokenUsage(account, LocalDate.now(), 0));

        int limit = getAccountTokenLimit(account);
        int estimatedTokens = estimateAITokenUsage(request.getPrompt());

        if (usage.getTokensUsed() + estimatedTokens > limit) {
            throw new AppException(ErrorCode.OUT_OF_LIMIT);
        }

        String response = aiService.chatWithPlatformContext(request.getPrompt());
        int responseTokens = estimateAITokenUsage(response);

        usage.setTokensUsed(usage.getTokensUsed() + estimatedTokens + responseTokens);
        aiTokenUsageRepository.save(usage);

        return ChatbotResponse.builder()
                .message(response)
                .build();
    }

    @Override
    public int getAccountTokenLimit(Account account) {
        switch (account.getRole()) {
            case Role.ADMIN -> {
                return AIToken.ADMIN_LIMIT;
            }
            case Role.MEMBER -> {
                return account.isHavingSubscription() ?
                        AIToken.MEMBER_PREMIUM_LIMIT
                        :
                        AIToken.MEMBER_FREE_LIMIT;
            }
            case Role.COACH -> {
                return AIToken.COACH_LIMIT;
            }
            default -> {
                return 0;
            }
        }
    }

    @Override
    public int estimateAITokenUsage(String content) {
        return (int) Math.ceil((double) content.length() / AIToken.CHARACTER_TO_TOKEN_DEFINITION);
    }
}
