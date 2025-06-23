package com.swpteam.smokingcessation.service.interfaces.profile;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.review.ReviewCreateRequest;
import com.swpteam.smokingcessation.domain.dto.review.ReviewResponse;
import com.swpteam.smokingcessation.domain.dto.review.ReviewUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Review;
import org.springframework.data.domain.Page;

public interface IReviewService {
    Page<ReviewResponse> getReviewPage(PageableRequest request);

    Page<ReviewResponse> getReviewPageByAccount(String accountId, PageableRequest request);

    Page<ReviewResponse> getReviewPageByCoach(String coachId, PageableRequest request);

    ReviewResponse getReviewById(String id);

    ReviewResponse createReview(ReviewCreateRequest request);

    ReviewResponse updateReview(String id, ReviewUpdateRequest request);

    void softDeleteReview(String id);

    Review findReviewById(String id);
}
