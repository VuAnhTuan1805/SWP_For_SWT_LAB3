package com.swpteam.smokingcessation.service.impl.booking;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.booking.BookingRequest;
import com.swpteam.smokingcessation.domain.dto.booking.BookingResponse;
import com.swpteam.smokingcessation.domain.dto.booking.BookingUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Booking;
import com.swpteam.smokingcessation.domain.entity.Coach;
import com.swpteam.smokingcessation.domain.enums.BookingStatus;
import com.swpteam.smokingcessation.domain.mapper.BookingMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.AccountRepository;
import com.swpteam.smokingcessation.repository.BookingRepository;
import com.swpteam.smokingcessation.repository.CoachRepository;
import com.swpteam.smokingcessation.repository.TimeTableRepository;
import com.swpteam.smokingcessation.service.impl.profile.CoachServiceImpl;
import com.swpteam.smokingcessation.service.interfaces.booking.IBookingService;
import com.swpteam.smokingcessation.utils.AccountUtilService;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService {

    BookingRepository bookingRepository;
    BookingMapper bookingMapper;
    AccountRepository accountRepository;
    CoachRepository coachRepository;
    GoogleCalendarService googleCalendarService;
    TimeTableRepository timeTableRepository;
    AccountUtilService accountUtilService;
    CoachServiceImpl coachService;

    @Override
    public Page<BookingResponse> getBookingPage(PageableRequest request) {
        if (!ValidationUtil.isFieldExist(Booking.class, request.getSortBy())) {
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
        }

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Booking> bookings = bookingRepository.findAllByIsDeletedFalse(pageable);

        return bookings.map(bookingMapper::toResponse);
    }

    @Override
    public BookingResponse getBookingById(String id) {
        Booking booking = bookingRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));

        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public BookingResponse createBooking(BookingRequest request) {
        Account account = accountRepository.findByIdAndIsDeletedFalse(request.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));
        Coach coach = coachRepository.findByIdAndIsDeletedFalse(request.getCoachId())
                .orElseThrow(() -> new AppException(ErrorCode.COACH_NOT_FOUND));

        boolean inWorkingTime = timeTableRepository
                //check if booking in worktime coachId
                .findByCoachIdAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(
                        request.getCoachId(), request.getStartedAt(), request.getEndedAt()
                ).isPresent();

        if (!inWorkingTime) {
            throw new AppException(ErrorCode.BOOKING_OUT_OF_WORKING_TIME);
        }
        //check if booking duplicated
        boolean isOverlapped = bookingRepository.existsByCoachIdAndIsDeletedFalseAndStartedAtLessThanAndEndedAtGreaterThan(
                request.getCoachId(),
                request.getEndedAt(),
                request.getStartedAt()
        );
        if (isOverlapped) {
            throw new AppException(ErrorCode.BOOKING_TIME_CONFLICT);
        }

        Booking booking = bookingMapper.toEntity(request);
        booking.setAccount(account);
        booking.setCoach(coach);
        booking.setStatus(BookingStatus.PENDING);

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public BookingResponse updateBookingById(String id, BookingUpdateRequest request) {
        Booking booking = bookingRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));

        bookingMapper.update(booking, request);

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public void DeleteBookingById(String id) {
        Booking booking = bookingRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));

        booking.setDeleted(true);
        bookingRepository.delete(booking);
    }

    @Override
    @Transactional
    public BookingResponse createBookingWithMeet(BookingRequest request) {
        Account account = accountRepository.findByIdAndIsDeletedFalse(request.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Coach coach = coachRepository.findByIdAndIsDeletedFalse(request.getCoachId())
                .orElseThrow(() -> new AppException(ErrorCode.COACH_NOT_FOUND));

        boolean inWorkingTime = timeTableRepository
                .findByCoachIdAndStartedAtLessThanEqualAndEndedAtGreaterThanEqual(
                        request.getCoachId(), request.getStartedAt(), request.getEndedAt()
                ).isPresent();

        if (!inWorkingTime) {
            throw new AppException(ErrorCode.BOOKING_OUT_OF_WORKING_TIME);
        }

        boolean isOverlapped = bookingRepository.existsByCoachIdAndIsDeletedFalseAndStartedAtLessThanAndEndedAtGreaterThan(
                request.getCoachId(),
                request.getEndedAt(),
                request.getStartedAt()
        );
        if (isOverlapped) {
            throw new AppException(ErrorCode.BOOKING_TIME_CONFLICT);
        }

        String meetingUrl = null;
        try {
            meetingUrl = googleCalendarService.createGoogleMeetEvent(
                    request.getAccessToken(),
                    request.getStartedAt().toString(),
                    request.getEndedAt().toString()
            );
        } catch (Exception e) {
            throw new AppException(ErrorCode.GOOGLE_CALENDAR_ERROR);
        }

        Booking booking = bookingMapper.toEntity(request);
        booking.setAccount(account);
        booking.setCoach(coach);
        booking.setMeetLink(meetingUrl);
        booking.setStatus(BookingStatus.PENDING);

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }


}