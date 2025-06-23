package com.swpteam.smokingcessation.controller.v1.profile;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.coach.CoachRequest;
import com.swpteam.smokingcessation.domain.dto.coach.CoachResponse;
import com.swpteam.smokingcessation.service.interfaces.profile.ICoachService;
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
@RequestMapping("/api/v1/coaches")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Coach", description = "Manage coach-related operations")
public class CoachController {
    ICoachService coachService;

    @GetMapping
    ResponseEntity<ApiResponse<Page<CoachResponse>>> getCoachPage(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<CoachResponse>>builder()
                        .code(SuccessCode.COACH_GET_ALL.getCode())
                        .message(SuccessCode.COACH_GET_ALL.getMessage())
                        .result(coachService.getCoachPage(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<CoachResponse>> getCoachById(@PathVariable String id) {
        CoachResponse response = coachService.getCoachById(id);
        return ResponseEntity.ok(
                ApiResponse.<CoachResponse>builder()
                        .code(SuccessCode.COACH_GET_BY_ID.getCode())
                        .message(SuccessCode.COACH_GET_BY_ID.getMessage())
                        .result(response)
                        .build()
        );
    }

    @PostMapping
    ResponseEntity<ApiResponse<CoachResponse>> createCoach(@Valid @RequestBody CoachRequest request) {
        CoachResponse response = coachService.createCoach(request);
        return ResponseEntity.ok(
                ApiResponse.<CoachResponse>builder()
                        .code(SuccessCode.COACH_CREATED.getCode())
                        .message(SuccessCode.COACH_CREATED.getMessage())
                        .result(response)
                        .build()
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<CoachResponse>> updateCoachById(@PathVariable String id, @Valid @RequestBody CoachRequest request) {
        CoachResponse response = coachService.updateCoachById(id, request);
        return ResponseEntity.ok(
                ApiResponse.<CoachResponse>builder()
                        .code(SuccessCode.COACH_UPDATED.getCode())
                        .message(SuccessCode.COACH_UPDATED.getMessage())
                        .result(response)
                        .build()
        );
    }
    
}