package com.swpteam.smokingcessation.websocket;

import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.enums.AccountStatus;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class WebSocketPresenceEventListener {

    @Autowired
    AccountRepository accountRepository;

    // Map sessionId -> accountId
    private final Map<String, String> sessionAccountMap = new ConcurrentHashMap<>();

    @EventListener
    @Transactional
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String accountId = accessor.getFirstNativeHeader("accountId");
        String sessionId = accessor.getSessionId();
        if (accountId != null && sessionId != null) {
            sessionAccountMap.put(sessionId, accountId);
            Account account = accountRepository.findByIdAndIsDeletedFalse(accountId)
                    .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
            account.setStatus(AccountStatus.ONLINE);
            accountRepository.save(account);
            log.info("Account {} connected (session: {})", accountId, sessionId);
        }
    }

    @EventListener
    @Transactional
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        String accountId = sessionAccountMap.remove(sessionId);
        if (accountId != null) {
            accountRepository.findByIdAndIsDeletedFalse(accountId).ifPresent(account -> {
                account.setStatus(AccountStatus.OFFLINE);
                accountRepository.save(account);
                log.info("Account {} is now OFFLINE", accountId);
            });
        }
    }
}
