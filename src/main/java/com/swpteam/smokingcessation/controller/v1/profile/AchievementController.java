package com.swpteam.smokingcessation.controller.v1.profile;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.achievement.*;
import com.swpteam.smokingcessation.service.interfaces.profile.IAchievementService;
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
@RequestMapping("/api/v1/achievements")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Achievement", description = "Manage achievement catalog")
public class AchievementController {

    IAchievementService achievementService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AchievementResponse>>> getAchievementPage(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<AchievementResponse>>builder()
                        .code(SuccessCode.ACHIEVEMENT_GET_ALL.getCode())
                        .message(SuccessCode.ACHIEVEMENT_GET_ALL.getMessage())
                        .result(achievementService.getAchievementPage(request))
                        .build()
        );
    }

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse<AchievementResponse>> getAchievementByName(@PathVariable String name) {
        return ResponseEntity.ok(
                ApiResponse.<AchievementResponse>builder()
                        .code(SuccessCode.ACHIEVEMENT_GET_BY_NAME.getCode())
                        .message(SuccessCode.ACHIEVEMENT_GET_BY_NAME.getMessage())
                        .result(achievementService.getAchievementByName(name))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AchievementResponse>> createAchievement(
            @RequestBody @Valid AchievementCreateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<AchievementResponse>builder()
                        .code(SuccessCode.ACHIEVEMENT_CREATED.getCode())
                        .message(SuccessCode.ACHIEVEMENT_CREATED.getMessage())
                        .result(achievementService.createAchievement(request))
                        .build()
        );
    }

    @PutMapping("/{name}")
    public ResponseEntity<ApiResponse<AchievementResponse>> updateAchievement(
            @PathVariable String name,
            @RequestBody @Valid AchievementUpdateRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<AchievementResponse>builder()
                        .code(SuccessCode.ACHIEVEMENT_UPDATED.getCode())
                        .message(SuccessCode.ACHIEVEMENT_UPDATED.getMessage())
                        .result(achievementService.updateAchievement(name, request))
                        .build()
        );
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ApiResponse<String>> deleteAchievement(@PathVariable String name) {
        achievementService.softDeleteAchievement(name);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code(SuccessCode.ACHIEVEMENT_DELETED.getCode())
                        .message(SuccessCode.ACHIEVEMENT_DELETED.getMessage())
                        .build()
        );
    }
}
