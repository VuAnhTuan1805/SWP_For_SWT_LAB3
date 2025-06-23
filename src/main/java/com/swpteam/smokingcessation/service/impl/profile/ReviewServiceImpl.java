package com.swpteam.smokingcessation.service.impl.profile;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.review.ReviewCreateRequest;
import com.swpteam.smokingcessation.domain.dto.review.ReviewResponse;
import com.swpteam.smokingcessation.domain.dto.review.ReviewUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Review;
import com.swpteam.smokingcessation.domain.mapper.ReviewMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.ReviewRepository;
import com.swpteam.smokingcessation.service.interfaces.identity.IAccountService;
import com.swpteam.smokingcessation.service.interfaces.profile.IReviewService;
import com.swpteam.smokingcessation.utils.AuthUtil;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewServiceImpl implements IReviewService {

    ReviewRepository reviewRepository;
    ReviewMapper reviewMapper;
    AuthUtil authUtil;
    IAccountService accountService;

    @Override
    public Page<ReviewResponse> getReviewPage(PageableRequest request) {
        ValidationUtil.checkFieldExist(Review.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Review> reviews = reviewRepository.findAllByIsDeletedFalse(pageable);

        return reviews.map(reviewMapper::toResponse);
    }

    @Override
    public Page<ReviewResponse> getReviewPageByAccount(String accountId, PageableRequest request) {
        ValidationUtil.checkFieldExist(Review.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Review> reviews = reviewRepository.findByMemberIdAndIsDeletedFalse(accountId, pageable);

        return reviews.map(reviewMapper::toResponse);
    }

    @Override
    public Page<ReviewResponse> getReviewPageByCoach(String coachId, PageableRequest request) {
        ValidationUtil.checkFieldExist(Review.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Review> reviews = reviewRepository.findByCoachIdAndIsDeletedFalse(coachId, pageable);

        return reviews.map(reviewMapper::toResponse);
    }


    @Override
    public ReviewResponse getReviewById(String id) {
        return reviewMapper.toResponse(findReviewById(id));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('MEMBER')")
    @CachePut(value = "REVIEW_CACHE", key = "#result.id")
    public ReviewResponse createReview(ReviewCreateRequest request) {
        Account currentAccount = authUtil.getCurrentAccountOrThrow();

        Account coach = accountService.findAccountById(request.getCoachId());

        Review review = reviewMapper.toEntity(request);
        review.setMember(currentAccount);
        review.setCoach(coach);

        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MEMBER')")
    @CachePut(value = "REVIEW_CACHE", key = "#id")
    public ReviewResponse updateReview(String id, ReviewUpdateRequest request) {
        Account currentAccount = authUtil.getCurrentAccountOrThrow();

        Review review = findReviewById(id);
        if (!review.getMember().getId().equals(currentAccount.getId())) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        reviewMapper.update(review, request);
        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    @Transactional
    @CacheEvict(value = "REVIEW_CACHE", key = "#id")
    @PreAuthorize("hasanyRole('ADMIN', 'MEMBER')")
    public void softDeleteReview(String id) {
        Review review = findReviewById(id);
        Account currentAccount = authUtil.getCurrentAccountOrThrow();

        if (authUtil.isAdminOrOwner(currentAccount.getId()) || currentAccount == review.getCoach()) {
            review.setDeleted(true);
            reviewRepository.save(review);

        } else {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
    }

    @Override
    @Transactional
    @Cacheable(value = "REVIEW_CACHE", key = "#id")
    public Review findReviewById(String id) {
        Review review = reviewRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        if (review.getMember().isDeleted() || review.getCoach().isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_DELETED);
        }

        return review;
    }
}
