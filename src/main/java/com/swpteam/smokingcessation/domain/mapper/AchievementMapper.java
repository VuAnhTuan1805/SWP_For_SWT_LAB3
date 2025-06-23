package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.achievement.*;
import com.swpteam.smokingcessation.domain.entity.Achievement;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AchievementMapper {

    Achievement toEntity(AchievementCreateRequest request);

    AchievementResponse toResponse(Achievement entity);

    void update(@MappingTarget Achievement entity, AchievementUpdateRequest request);
}
