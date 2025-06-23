package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.feedback.FeedbackRequest;
import com.swpteam.smokingcessation.domain.dto.feedback.FeedbackResponse;
import com.swpteam.smokingcessation.domain.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    Feedback toEntity(FeedbackRequest request);

    @Mapping(source = "account.id", target = "accountId")
    FeedbackResponse toRespone(Feedback feedback);

    void update(@MappingTarget Feedback feedback, FeedbackRequest request);
}
