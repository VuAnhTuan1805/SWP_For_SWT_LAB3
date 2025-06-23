package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.review.ReviewCreateRequest;
import com.swpteam.smokingcessation.domain.dto.review.ReviewResponse;
import com.swpteam.smokingcessation.domain.dto.review.ReviewUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "coach.id", target = "coachId")
    ReviewResponse toResponse(Review review);

    Review toEntity(ReviewCreateRequest request);

    void update(@MappingTarget Review review, ReviewUpdateRequest request);
}
