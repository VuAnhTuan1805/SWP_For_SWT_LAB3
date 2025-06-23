package com.swpteam.smokingcessation.service.interfaces.tracking;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.record.RecordHabitCreateRequest;
import com.swpteam.smokingcessation.domain.dto.record.RecordHabitResponse;
import com.swpteam.smokingcessation.domain.dto.record.RecordHabitUpdateRequest;
import org.springframework.data.domain.Page;

public interface IRecordHabitService {

    Page<RecordHabitResponse> getRecordPage(PageableRequest request);

    RecordHabitResponse getRecordById(String id);

    Page<RecordHabitResponse> getRecordPageByAccountId(String accountId, PageableRequest request);

    RecordHabitResponse createRecord(RecordHabitCreateRequest request);

    RecordHabitResponse updateRecord(String id, RecordHabitUpdateRequest request);

    void softDeleteRecordById(String id);
}
