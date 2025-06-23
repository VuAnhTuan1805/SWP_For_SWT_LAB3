package com.swpteam.smokingcessation.schedule;

import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Member;
import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.domain.entity.Streak;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.MemberRepository;
import com.swpteam.smokingcessation.repository.RecordHabitRepository;
import com.swpteam.smokingcessation.repository.SettingRepository;
import com.swpteam.smokingcessation.repository.StreakRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StreakScheduler {
    SettingRepository settingRepository;
    RecordHabitRepository recordHabitRepository;
    StreakRepository streakRepository;
    MemberRepository memberRepository;

    @Scheduled(cron = "0 * * * * *")
    public void checkAndResetStreak() {
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        List<Setting> settings = settingRepository.findAllByIsDeletedFalse();

        for (Setting setting : settings) {
            try {
                LocalTime deadline = setting.getReportDeadline();
                String accountId = setting.getAccount().getId();

                LocalDateTime deadlineDateTime = LocalDateTime.of(today, deadline);

                if (!now.isAfter(deadlineDateTime)) {
                    return;
                }

                boolean hasRecord = recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(accountId, today);

                Streak streak = streakRepository.findByMember_Account_Id(accountId).orElse(null);
                if (streak == null || hasRecord || streak.getStreak() == 0) {
                    return;
                }

                Member member = streak.getMember();

                if (member.getHighestStreak() < streak.getStreak()) {
                    member.setHighestStreak(streak.getStreak());
                    memberRepository.save(member);
                }
                streak.setStreak(0);
                streakRepository.save(streak);
            } catch (Exception e) {
                log.error("Failed to reset streak for setting accountId: {}", setting.getAccount().getId(), e);
                throw new AppException(ErrorCode.STREAK_RESET_FAILED);
            }
        }
    }
}
