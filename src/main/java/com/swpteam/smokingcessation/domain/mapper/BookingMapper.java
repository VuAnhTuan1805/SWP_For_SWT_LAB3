package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.booking.BookingRequest;
import com.swpteam.smokingcessation.domain.dto.booking.BookingResponse;
import com.swpteam.smokingcessation.domain.dto.booking.BookingUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "coach.id", target = "coachId")
    BookingResponse toResponse(Booking booking);

    Booking toEntity(BookingRequest bookingRequest);

    void update(@MappingTarget Booking booking, BookingUpdateRequest request);
}