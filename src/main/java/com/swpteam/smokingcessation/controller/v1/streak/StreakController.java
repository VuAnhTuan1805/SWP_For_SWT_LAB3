package com.swpteam.smokingcessation.controller.v1.streak;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.streak.StreakRequest;
import com.swpteam.smokingcessation.domain.dto.streak.StreakResponse;
import com.swpteam.smokingcessation.service.interfaces.streak.IStreakService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/streaks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Streak", description = "Manage streak-related operations")
public class StreakController {
    IStreakService streakService;

    @PostMapping("/{memberId}")
    ResponseEntity<ApiResponse<StreakResponse>> createStreak(@PathVariable String memberId, @RequestBody @Valid StreakRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<StreakResponse>builder()
                        .code(SuccessCode.STREAK_CREATED.getCode())
                        .message(SuccessCode.STREAK_CREATED.getMessage())
                        .result(streakService.createStreak(memberId, request))
                        .build());
    }

    @GetMapping
    ResponseEntity<ApiResponse<Page<StreakResponse>>> getStreaks(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<StreakResponse>>builder()
                        .result(streakService.getStreakPage(request))
                        .build());
    }

    @GetMapping("/{memberId}")
    ResponseEntity<ApiResponse<StreakResponse>> getStreakById(@PathVariable String memberId) {
        return ResponseEntity.ok(
                ApiResponse.<StreakResponse>builder()
                        .result(streakService.getStreakById(memberId))
                        .build());
    }

    @PutMapping("/{memberId}")
    ResponseEntity<ApiResponse<StreakResponse>> updateStreak(@PathVariable String memberId, @RequestBody @Valid StreakRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<StreakResponse>builder()
                        .code(SuccessCode.STREAK_UPDATED.getCode())
                        .message(SuccessCode.STREAK_UPDATED.getMessage())
                        .result(streakService.updateStreak(memberId, request))
                        .build());
    }

    @DeleteMapping("/{memberId}")
    ResponseEntity<ApiResponse<Void>> deleteStreak(@PathVariable String memberId) {
        streakService.deleteStreak(memberId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.STREAK_DELETED.getCode())
                        .message(SuccessCode.STREAK_DELETED.getMessage())
                        .build());
    }

    @PutMapping("/reset/{memberId}")
    ResponseEntity<ApiResponse<Void>> resetStreak(@PathVariable String memberId) {
        streakService.resetStreak(memberId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.STREAK_RESET.getCode())
                        .message(SuccessCode.STREAK_RESET.getMessage())
                        .build());
    }
}
