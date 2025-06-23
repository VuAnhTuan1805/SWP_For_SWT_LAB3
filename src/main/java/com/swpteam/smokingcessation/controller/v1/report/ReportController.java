package com.swpteam.smokingcessation.controller.v1.report;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.report.ReportSummaryRequest;
import com.swpteam.smokingcessation.domain.dto.report.ReportSummaryResponse;
import com.swpteam.smokingcessation.service.interfaces.report.IReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Report", description = "Manage report-related operations")
public class ReportController {

    IReportService reportService;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<ReportSummaryResponse>> getReportSummary(@Valid ReportSummaryRequest reportSummaryRequest) {
        return ResponseEntity.ok(
                ApiResponse.<ReportSummaryResponse>builder()
                        .code(SuccessCode.SUMMARY_GET.getCode())
                        .message(SuccessCode.SUMMARY_GET.getMessage())
                        .result(reportService.getSummary(reportSummaryRequest))
                        .build()
        );
    }
}
