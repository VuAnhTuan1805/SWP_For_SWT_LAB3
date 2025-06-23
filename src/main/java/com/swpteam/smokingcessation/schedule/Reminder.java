package com.swpteam.smokingcessation.schedule;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.integration.mail.MailServiceImpl;
import com.swpteam.smokingcessation.domain.entity.Message;
import com.swpteam.smokingcessation.repository.MessageRepository;
import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.repository.SettingRepository;
import com.swpteam.smokingcessation.domain.enums.MotivationFrequency;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Reminder {

    MessageRepository messageRepository;
    MailServiceImpl mailServiceImpl;
    SettingRepository settingRepository;
    Random random = new Random();

    @Scheduled(cron = "0 * * * * *")
    public void sendReminders() {
        LocalTime currentTime = LocalTime.now().withSecond(0).withNano(0);
        LocalTime deadlineIn30Minutes = currentTime.plusMinutes(30);

        List<Setting> settings = settingRepository.findByReportDeadlineAndIsDeletedFalse(deadlineIn30Minutes);

        for (Setting setting : settings) {
            try {
                Account account = setting.getAccount();
                String userEmail = account.getEmail();

                log.info("Sending reminder to user with email: {}", userEmail);

                mailServiceImpl.sendReminderMail(userEmail);
            } catch (Exception e) {
                log.error("Failed to send reminder for setting accountId: {}", setting.getAccount().getId(), e);
            }
        }
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void sendDailyMotivation() {
        sendMotivation(MotivationFrequency.DAILY);
    }

    @Scheduled(cron = "0 0 8,14,20,0 * * *")
    public void sendEvery6HoursMotivation() {
        sendMotivation(MotivationFrequency.EVERY6HOURS);
    }

    @Scheduled(cron = "0 0 8,20 * * *")
    public void sendEvery12HoursMotivation() {
        sendMotivation(MotivationFrequency.EVERY12HOURS);
    }

    @Scheduled(cron = "0 0 8 * * SUN")
    public void sendWeeklyMotivation() {
        sendMotivation(MotivationFrequency.WEEKLY);
    }

    @Scheduled(cron = "0 0 8 1 * *")
    public void sendMonthlyMotivation() {
        sendMotivation(MotivationFrequency.MONTHLY);
    }

    private Message getRandomMotivationMessage() {
        List<Message> motivationMessages = messageRepository.findAllByIsDeletedFalse();

        int randomIndex = random.nextInt(motivationMessages.size());

        return motivationMessages.get(randomIndex);
    }

    private void sendMotivation(MotivationFrequency motivationFrequency) {
        log.info("Sending motivation with frequency: {}", motivationFrequency);

        List<Setting> dailySettings = settingRepository.findByMotivationFrequencyAndIsDeletedFalse(motivationFrequency);
        if (dailySettings.isEmpty()) {
            log.info("No users found with {} motivation setting", motivationFrequency);
            return;
        }

        log.info("Sending motivation messages to {} user(s)", dailySettings.size());
        for (Setting setting : dailySettings) {
            String email = setting.getAccount().getEmail();
            Message randomMotivationMessage = getRandomMotivationMessage();
            try {
                mailServiceImpl.sendMotivationMail(email, randomMotivationMessage);
            } catch (Exception e) {
                log.error("Failed to send motivation to email: {}", email, e);
            }
        }
    }
}