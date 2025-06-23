package com.swpteam.smokingcessation.schedule;

import com.swpteam.smokingcessation.repository.RefreshTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenCleanUp {

    RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void cleanExpiredTokens() {
        Date now = new Date();
        refreshTokenRepository.deleteAllByExpiryTimeBefore(now);
    }
}
