package com.swpteam.smokingcessation.service.interfaces.report;

import com.swpteam.smokingcessation.domain.dto.report.ReportSummaryRequest;
import com.swpteam.smokingcessation.domain.dto.report.ReportSummaryResponse;

public interface IReportService {
    ReportSummaryResponse getSummary(ReportSummaryRequest reportSummaryRequest);
}
