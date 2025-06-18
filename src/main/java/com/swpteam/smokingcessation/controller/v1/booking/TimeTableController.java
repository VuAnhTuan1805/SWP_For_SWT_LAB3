package com.swpteam.smokingcessation.controller.v1.booking;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.TimeTable.TimeTableRequest;
import com.swpteam.smokingcessation.domain.dto.TimeTable.TimeTableResponse;
import com.swpteam.smokingcessation.service.interfaces.booking.ITimeTableService;
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
@RequestMapping("/api/v1/timetables")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "TimeTable", description = "Manage timetable-related operations")
public class TimeTableController {

    ITimeTableService timeTableService;

    @GetMapping
    ResponseEntity<ApiResponse<Page<TimeTableResponse>>> getTimeTablePage(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<TimeTableResponse>>builder()
                        .code(SuccessCode.TIMETABLE_GET_ALL.getCode())
                        .message(SuccessCode.TIMETABLE_GET_ALL.getMessage())
                        .result(timeTableService.getTimeTablePage(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<TimeTableResponse>> getTimeTableById(@PathVariable String id) {
        TimeTableResponse response = timeTableService.getTimeTableById(id);
        return ResponseEntity.ok(
                ApiResponse.<TimeTableResponse>builder()
                        .code(SuccessCode.TIMETABLE_GET_BY_ID.getCode())
                        .message(SuccessCode.TIMETABLE_GET_BY_ID.getMessage())
                        .result(response)
                        .build()
        );
    }

    @GetMapping("/coach/{coachId}")
    ResponseEntity<ApiResponse<Page<TimeTableResponse>>> getTimeTablesByCoachId(@PathVariable String coachId, @Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<TimeTableResponse>>builder()
                        .code(SuccessCode.TIMETABLE_GET_BY_COACH_ID.getCode())
                        .message(SuccessCode.TIMETABLE_GET_BY_COACH_ID.getMessage())
                        .result(timeTableService.getTimeTablesByCoachId(coachId, request))
                        .build()
        );
    }

    @PostMapping
    ResponseEntity<ApiResponse<TimeTableResponse>> createTimeTable(@Valid @RequestBody TimeTableRequest request) {
        TimeTableResponse response = timeTableService.createTimeTable(request);
        return ResponseEntity.ok(
                ApiResponse.<TimeTableResponse>builder()
                        .code(SuccessCode.TIMETABLE_CREATED.getCode())
                        .message(SuccessCode.TIMETABLE_CREATED.getMessage())
                        .result(response)
                        .build()
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<TimeTableResponse>> updateTimeTableById(@PathVariable String id, @Valid @RequestBody TimeTableRequest request) {
        TimeTableResponse response = timeTableService.updateTimeTableById(id, request);
        return ResponseEntity.ok(
                ApiResponse.<TimeTableResponse>builder()
                        .code(SuccessCode.TIMETABLE_UPDATED.getCode())
                        .message(SuccessCode.TIMETABLE_UPDATED.getMessage())
                        .result(response)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Void>> deleteTimeTableById(@PathVariable String id) {
        timeTableService.softDeleteTimeTableById(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.TIMETABLE_DELETED.getCode())
                        .message(SuccessCode.TIMETABLE_DELETED.getMessage())
                        .build()
        );
    }
}