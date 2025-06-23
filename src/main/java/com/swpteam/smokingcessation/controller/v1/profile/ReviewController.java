package com.swpteam.smokingcessation.controller.v1.profile;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.review.ReviewCreateRequest;
import com.swpteam.smokingcessation.domain.dto.review.ReviewResponse;
import com.swpteam.smokingcessation.domain.dto.review.ReviewUpdateRequest;
import com.swpteam.smokingcessation.service.interfaces.profile.IReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Review", description = "Manage review-related operations")
public class ReviewController {

    IReviewService reviewService;

    @GetMapping
    ResponseEntity<ApiResponse<Page<ReviewResponse>>> getReviewPage(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<ReviewResponse>>builder()
                        .code(SuccessCode.REVIEW_GET_ALL.getCode())
                        .message(SuccessCode.REVIEW_GET_ALL.getMessage())
                        .result(reviewService.getReviewPage(request))
                        .build()
        );
    }

    @GetMapping("/by-account/{accountId}")
    ResponseEntity<ApiResponse<Page<ReviewResponse>>> getReviewByAccount(
            @PathVariable String accountId,
            @Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<ReviewResponse>>builder()
                        .code(SuccessCode.REVIEW_GET_BY_ACCOUNT.getCode())
                        .message(SuccessCode.REVIEW_GET_BY_ACCOUNT.getMessage())
                        .result(reviewService.getReviewPageByAccount(accountId, request))
                        .build()
        );
    }

    @GetMapping("/by-coach/{coachId}")
    ResponseEntity<ApiResponse<Page<ReviewResponse>>> getReviewByCoach(
            @PathVariable String coachId,
            @Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<ReviewResponse>>builder()
                        .code(SuccessCode.REVIEW_GET_BY_COACH.getCode())
                        .message(SuccessCode.REVIEW_GET_BY_COACH.getMessage())
                        .result(reviewService.getReviewPageByCoach(coachId, request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<ReviewResponse>> getReviewById(@PathVariable String id) {
        return ResponseEntity.ok(
                ApiResponse.<ReviewResponse>builder()
                        .code(SuccessCode.REVIEW_GET_BY_ID.getCode())
                        .message(SuccessCode.REVIEW_GET_BY_ID.getMessage())
                        .result(reviewService.getReviewById(id))
                        .build()
        );
    }

    @PostMapping
    ResponseEntity<ApiResponse<ReviewResponse>> createReview(@RequestBody @Valid ReviewCreateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<ReviewResponse>builder()
                        .code(SuccessCode.REVIEW_CREATED.getCode())
                        .message(SuccessCode.REVIEW_CREATED.getMessage())
                        .result(reviewService.createReview(request))
                        .build()
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<ReviewResponse>> updateReview(@PathVariable String id,
                                                             @RequestBody @Valid ReviewUpdateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<ReviewResponse>builder()
                        .code(SuccessCode.REVIEW_UPDATED.getCode())
                        .message(SuccessCode.REVIEW_UPDATED.getMessage())
                        .result(reviewService.updateReview(id, request))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<String>> softDeleteReview(@PathVariable String id) {
        reviewService.softDeleteReview(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(SuccessCode.REVIEW_DELETED.getCode())
                        .message(SuccessCode.REVIEW_DELETED.getMessage())
                        .build()
        );
    }
}
