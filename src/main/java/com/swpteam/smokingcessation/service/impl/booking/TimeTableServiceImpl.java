package com.swpteam.smokingcessation.service.impl.booking;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;

import com.swpteam.smokingcessation.domain.dto.TimeTable.TimeTableRequest;
import com.swpteam.smokingcessation.domain.dto.TimeTable.TimeTableResponse;
import com.swpteam.smokingcessation.domain.entity.Coach;
import com.swpteam.smokingcessation.domain.entity.TimeTable;
import com.swpteam.smokingcessation.domain.mapper.TimeTableMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.CoachRepository;
import com.swpteam.smokingcessation.repository.TimeTableRepository;
import com.swpteam.smokingcessation.service.interfaces.booking.ITimeTableService;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TimeTableServiceImpl implements ITimeTableService {

    TimeTableRepository timeTableRepository;
    TimeTableMapper timeTableMapper;
    CoachRepository coachRepository;


    @Override
    public Page<TimeTableResponse> getTimeTablePage(PageableRequest request) {
        if (!ValidationUtil.isFieldExist(TimeTable.class, request.getSortBy())) {
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
        }
        Pageable pageable = PageableRequest.getPageable(request);
        Page<TimeTable> timeTables = timeTableRepository.findAllByIsDeletedFalse(pageable);

        return timeTables.map(timeTableMapper::toResponse);
    }

    @Override
    public TimeTableResponse getTimeTableById(String id) {
        TimeTable timeTable = timeTableRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.TIMETABLE_NOT_FOUND));

        return timeTableMapper.toResponse(timeTable);
    }

    @Override
    public Page<TimeTableResponse> getTimeTablesByCoachId(String coachId, PageableRequest request) {
        if (!ValidationUtil.isFieldExist(TimeTable.class, request.getSortBy())) {
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
        }
        Pageable pageable = PageableRequest.getPageable(request);
        Page<TimeTable> timeTables = timeTableRepository.findByCoachIdAndIsDeletedFalse(coachId, pageable);

        return timeTables.map(timeTableMapper::toResponse);
    }

    @Override
    public TimeTableResponse createTimeTable(TimeTableRequest request) {
        TimeTable timeTable = timeTableMapper.toEntity(request);

        Coach coach = coachRepository.findByIdAndIsDeletedFalse(request.getCoachId())
                .orElseThrow(() -> new AppException(ErrorCode.COACH_NOT_FOUND));

        timeTable.setCoach(coach);

        return timeTableMapper.toResponse(timeTableRepository.save(timeTable));
    }

    @Override
    public TimeTableResponse updateTimeTableById(String id, TimeTableRequest request) {
        TimeTable timeTable = timeTableRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.TIMETABLE_NOT_FOUND));

        Coach coach = coachRepository.findByIdAndIsDeletedFalse(request.getCoachId())
                .orElseThrow(() -> new AppException(ErrorCode.COACH_NOT_FOUND));

        timeTableMapper.update(timeTable, request);
        timeTable.setCoach(coach);

        return timeTableMapper.toResponse(timeTableRepository.save(timeTable));
    }
    @Override
    public void softDeleteTimeTableById(String id) {
        TimeTable timeTable = timeTableRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.TIMETABLE_NOT_FOUND));

        timeTable.setDeleted(true);

        timeTableRepository.save(timeTable);
    }
}