package com.swpteam.smokingcessation.service.interfaces.booking;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.booking.BookingRequest;
import com.swpteam.smokingcessation.domain.dto.booking.BookingResponse;
import com.swpteam.smokingcessation.domain.dto.booking.BookingUpdateRequest;
import org.springframework.data.domain.Page;

public interface IBookingService {
    Page<BookingResponse> getBookingPage(PageableRequest request);

    BookingResponse getBookingById(String id);

    BookingResponse createBooking(BookingRequest request);

    BookingResponse updateBookingById(String id, BookingUpdateRequest request);

    void DeleteBookingById(String id);

    BookingResponse createBookingWithMeet(BookingRequest request);
}