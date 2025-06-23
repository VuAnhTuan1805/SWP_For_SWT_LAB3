package com.swpteam.smokingcessation.controller.v1.profile;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.feedback.FeedbackRequest;
import com.swpteam.smokingcessation.domain.dto.feedback.FeedbackResponse;
import com.swpteam.smokingcessation.service.interfaces.profile.IFeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Feedback", description = "Manage feedback catalog")
public class FeedbackController {
    IFeedbackService feedbackService;

    @GetMapping
    ResponseEntity<ApiResponse<Page<FeedbackResponse>>> getFeedbackPage(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<FeedbackResponse>>builder()
                        .code(SuccessCode.FEEDBACK_GET_ALL.getCode())
                        .message(SuccessCode.FEEDBACK_GET_ALL.getMessage())
                        .result(feedbackService.getFeedbackPage(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<FeedbackResponse>> getFeedbackById(@PathVariable String id) {
        return ResponseEntity.ok(
                ApiResponse.<FeedbackResponse>builder()
                        .code(SuccessCode.FEEDBACK_GET_BY_ID.getCode())
                        .message(SuccessCode.FEEDBACK_GET_BY_ID.getMessage())
                        .result(feedbackService.getFeedbackById(id))
                        .build()
        );
    }

    @GetMapping("/account/{id}")
    ResponseEntity<ApiResponse<Page<FeedbackResponse>>> getFeedbackPageByAccountId(@PathVariable String id, @jakarta.validation.Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<FeedbackResponse>>builder()
                        .code(SuccessCode.FEEDBACK_GET_BY_ACCOUNT.getCode())
                        .message(SuccessCode.FEEDBACK_GET_BY_ACCOUNT.getMessage())
                        .result(feedbackService.getFeedbackPageByAccountId(id, request))
                        .build()
        );
    }

    @PostMapping
    ResponseEntity<ApiResponse<FeedbackResponse>> createFeedback(@RequestBody @jakarta.validation.Valid FeedbackRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<FeedbackResponse>builder()
                        .code(SuccessCode.FEEDBACK_CREATED.getCode())
                        .message(SuccessCode.FEEDBACK_CREATED.getMessage())
                        .result(feedbackService.createFeedback(request))
                        .build()
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<FeedbackResponse>> updateFeedback(@PathVariable String id, @RequestBody @jakarta.validation.Valid FeedbackRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<FeedbackResponse>builder()
                        .code(SuccessCode.FEEDBACK_UPDATED.getCode())
                        .message(SuccessCode.FEEDBACK_UPDATED.getMessage())
                        .result(feedbackService.updateFeedback(id, request))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<String>> deleteFeedbackById(@PathVariable String id) {
        feedbackService.softDeleteFeedbackById(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(SuccessCode.FEEDBACK_DELETED.getCode())
                        .message(SuccessCode.FEEDBACK_DELETED.getMessage())
                        .build()
        );
    }

}
