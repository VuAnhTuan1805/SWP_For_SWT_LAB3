package com.swpteam.smokingcessation.schedule;

import com.swpteam.smokingcessation.domain.dto.report.ReportSummaryResponse;
import com.swpteam.smokingcessation.integration.mail.IMailService;
import com.swpteam.smokingcessation.repository.AccountRepository;
import com.swpteam.smokingcessation.repository.report.IReportRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MonthlyReportScheduler {
    AccountRepository accountRepository;
    IReportRepository reportRepository;
    IMailService mailService;


    @Scheduled(cron = "0 0 8 1 * *")
    public void generateAndSendMonthlyReport() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        ReportSummaryResponse report = reportRepository.getReportSummary(startOfMonth, endOfMonth);

        List<String> adminEmails = accountRepository.findAllAdminEmails();
        for (String email : adminEmails) {
            mailService.sendReportEmail(email, report);
        }
        log.info("Monthly report generated and sent to admins.");
    }
}
