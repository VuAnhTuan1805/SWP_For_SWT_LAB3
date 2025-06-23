package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.streak.StreakRequest;
import com.swpteam.smokingcessation.domain.dto.streak.StreakResponse;
import com.swpteam.smokingcessation.domain.entity.Streak;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StreakMapper {
    StreakResponse toResponse(Streak entity);

    Streak toEntity(StreakRequest request);

    void update(@MappingTarget Streak streak, StreakRequest request);
}
