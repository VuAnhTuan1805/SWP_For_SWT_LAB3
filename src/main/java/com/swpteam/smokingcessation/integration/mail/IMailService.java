package com.swpteam.smokingcessation.integration.mail;

import com.swpteam.smokingcessation.domain.dto.report.ReportSummaryResponse;
import com.swpteam.smokingcessation.domain.entity.Message;
import jakarta.mail.MessagingException;

public interface IMailService {

    void sendPaymentSuccessEmail(String accountId, String subscriptionId, double amount);

    void sendMotivationMail(String to, Message message);

    void sendReminderMail(String to);

    void sendResetPasswordEmail(String to, String resetLink, String userName) throws MessagingException;

    void sendReportEmail(String to, ReportSummaryResponse report);
}
