package com.swpteam.smokingcessation.service.impl.profile;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.feedback.FeedbackRequest;
import com.swpteam.smokingcessation.domain.dto.feedback.FeedbackResponse;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Feedback;
import com.swpteam.smokingcessation.domain.mapper.FeedbackMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.FeedbackRepository;
import com.swpteam.smokingcessation.service.interfaces.profile.IFeedbackService;
import com.swpteam.smokingcessation.utils.AuthUtil;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackServiceImpl implements IFeedbackService {

    FeedbackRepository feedbackRepository;
    FeedbackMapper feedbackMapper;
    AuthUtil authUtil;

    @Override
    public Page<FeedbackResponse> getFeedbackPage(PageableRequest request) {
        ValidationUtil.checkFieldExist(Feedback.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Feedback> feedbacks = feedbackRepository.findAllByIsDeletedFalse(pageable);
        return feedbacks.map(feedbackMapper::toRespone);
    }

    @Override
    public FeedbackResponse getFeedbackById(String id) {
        return feedbackMapper.toRespone(findFeedbackById(id));
    }

    @Override
    public Page<FeedbackResponse> getFeedbackPageByAccountId(String accountId, PageableRequest request) {
        ValidationUtil.checkFieldExist(Feedback.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Feedback> feedbacks = feedbackRepository.findByAccountIdAndIsDeletedFalse(accountId, pageable);

        return feedbacks.map(feedbackMapper::toRespone);
    }
    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MEMBER', 'COACH')")
    @CachePut(value = "FEEDBACK_CACHE", key = "#result.getId()")
    public FeedbackResponse createFeedback(FeedbackRequest request) {
        Account currentAccount = authUtil.getCurrentAccountOrThrow();

        Feedback feedback = feedbackMapper.toEntity(request);
        feedback.setAccount(currentAccount);

        return feedbackMapper.toRespone(feedbackRepository.save(feedback));
    }


    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MEMBER', 'COACH')")
    @CachePut(value = "FEEDBACK_CACHE", key = "#result.getId()")
    public FeedbackResponse updateFeedback(String id, FeedbackRequest request) {
        Account currentAccount = authUtil.getCurrentAccountOrThrow();

        Feedback feedback = findFeedbackById(id);
        if (!feedback.getAccount().getId().equals(currentAccount.getId())) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        feedbackMapper.update(feedback, request);
        return feedbackMapper.toRespone(feedbackRepository.save(feedback));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('MEMBER', 'COACH')")
    @CacheEvict(value = "FEEDBACK_CACHE", key = "#id")
    public void softDeleteFeedbackById(String id) {
        Feedback feedback = findFeedbackById(id);
        Account currentAccount = authUtil.getCurrentAccountOrThrow();

        if (authUtil.isAdminOrOwner(currentAccount.getId()) ||
                feedback.getAccount().getId().equals(currentAccount.getId())) {
            feedback.setDeleted(true);
            feedbackRepository.save(feedback);
        } else {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
    }

    @Cacheable(value = "FEEDBACK_CACHE", key = "#id")
    public Feedback findFeedbackById(String id) {
        Feedback feedback = feedbackRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.FEEDBACK_NOT_FOUND));

        if (feedback.getAccount().isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_DELETED);
        }

        return feedback;
    }

}
