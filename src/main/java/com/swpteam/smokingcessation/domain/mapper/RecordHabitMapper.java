package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.record.RecordHabitCreateRequest;
import com.swpteam.smokingcessation.domain.dto.record.RecordHabitResponse;
import com.swpteam.smokingcessation.domain.dto.record.RecordHabitUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.RecordHabit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RecordHabitMapper {

    RecordHabit toEntity(RecordHabitCreateRequest request);

    @Mapping(source = "account.id", target = "accountId")
    RecordHabitResponse toResponse(RecordHabit record);

    void update(@MappingTarget RecordHabit entity, RecordHabitUpdateRequest request);
}