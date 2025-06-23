package com.swpteam.smokingcessation.repository.report;

import com.swpteam.smokingcessation.domain.dto.report.ReportSummaryResponse;

import java.time.LocalDateTime;

public interface IReportRepository {
    ReportSummaryResponse getReportSummary(LocalDateTime from, LocalDateTime to);
}
