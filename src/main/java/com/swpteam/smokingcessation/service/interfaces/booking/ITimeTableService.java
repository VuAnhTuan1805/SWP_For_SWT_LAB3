package com.swpteam.smokingcessation.service.interfaces.booking;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.TimeTable.TimeTableRequest;
import com.swpteam.smokingcessation.domain.dto.TimeTable.TimeTableResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITimeTableService {
    Page<TimeTableResponse> getTimeTablePage(PageableRequest request);

    TimeTableResponse getTimeTableById(String id);

    Page<TimeTableResponse> getTimeTablesByCoachId(String coachId, PageableRequest request);

    TimeTableResponse createTimeTable(TimeTableRequest request);

    TimeTableResponse updateTimeTableById(String id, TimeTableRequest request);

    void softDeleteTimeTableById(String id);
}
