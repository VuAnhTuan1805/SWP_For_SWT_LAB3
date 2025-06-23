package com.swpteam.smokingcessation.controller.v1.booking;

import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.SuccessCode;
import com.swpteam.smokingcessation.domain.dto.booking.BookingRequest;
import com.swpteam.smokingcessation.domain.dto.booking.BookingResponse;
import com.swpteam.smokingcessation.service.interfaces.booking.IBookingService;
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
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Booking", description = "Manage booking-related operations")
public class BookingController {

    IBookingService bookingService;

    @GetMapping
    ResponseEntity<ApiResponse<Page<BookingResponse>>> getBookingPage(@Valid PageableRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<Page<BookingResponse>>builder()
                        .code(SuccessCode.BOOKING_GET_ALL.getCode())
                        .message(SuccessCode.BOOKING_GET_ALL.getMessage())
                        .result(bookingService.getBookingPage(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getBookingById(@PathVariable String id) {
        return ResponseEntity.ok(
                ApiResponse.<BookingResponse>builder()
                        .code(SuccessCode.BOOKING_GET_BY_ID.getCode())
                        .message(SuccessCode.BOOKING_GET_BY_ID.getMessage())
                        .result(bookingService.getBookingById(id))
                        .build()
        );
    }

    @PostMapping
    ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<BookingResponse>builder()
                        .code(SuccessCode.BOOKING_CREATED.getCode())
                        .message(SuccessCode.BOOKING_CREATED.getMessage())
                        .result(bookingService.createBooking(request))
                        .build()
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateBookingById(@PathVariable String id, @Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<BookingResponse>builder()
                        .code(SuccessCode.BOOKING_UPDATED.getCode())
                        .message(SuccessCode.BOOKING_UPDATED.getMessage())
                        .result(bookingService.updateBookingById(id, request))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> softDeleteBookingById(@PathVariable String id) {
        bookingService.softDeleteBookingById(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(SuccessCode.BOOKING_DELETED.getCode())
                        .message(SuccessCode.BOOKING_DELETED.getMessage())
                        .build()
        );
    }


    @PostMapping("/with-meet")
    public ResponseEntity<ApiResponse<BookingResponse>> createBookingWithMeet(@Valid @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.createBookingWithMeet(request);
        return ResponseEntity.ok(
                ApiResponse.<BookingResponse>builder()
                        .code(SuccessCode.BOOKING_CREATED.getCode())
                        .message(SuccessCode.BOOKING_CREATED.getMessage())
                        .result(response)
                        .build()
        );
    }
}