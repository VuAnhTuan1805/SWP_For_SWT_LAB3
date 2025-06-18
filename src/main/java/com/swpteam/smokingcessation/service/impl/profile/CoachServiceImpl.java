package com.swpteam.smokingcessation.service.impl.profile;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.coach.CoachRequest;
import com.swpteam.smokingcessation.domain.dto.coach.CoachResponse;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Coach;
import com.swpteam.smokingcessation.domain.mapper.CoachMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.AccountRepository;
import com.swpteam.smokingcessation.repository.CoachRepository;
import com.swpteam.smokingcessation.service.interfaces.profile.ICoachService;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CoachServiceImpl implements ICoachService {

    CoachRepository coachRepository;
    CoachMapper coachMapper;
    AccountRepository accountRepository;

    @Override
    public Page<CoachResponse> getCoachPage(PageableRequest request) {
        if (!ValidationUtil.isFieldExist(Coach.class, request.getSortBy())) {
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
        }
        Pageable pageable = PageableRequest.getPageable(request);
        Page<Coach> coaches = coachRepository.findAllByIsDeletedFalse(pageable);

        return coaches.map(coachMapper::toResponse);
    }

    @Override
    public CoachResponse getCoachById(String id) {
        Coach coach = coachRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.COACH_NOT_FOUND));

        return coachMapper.toResponse(coach);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    public CoachResponse createCoach(CoachRequest request) {
        Coach coach = coachMapper.toEntity(request);

        Account account = accountRepository.findByIdAndIsDeletedFalse(request.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        coach.setAccount(account);

        return coachMapper.toResponse(coachRepository.save(coach));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH)")
    public CoachResponse updateCoachById(String id, CoachRequest request) {
        Coach coach = coachRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.COACH_NOT_FOUND));
        coachMapper.update(coach, request);

        return coachMapper.toResponse(coachRepository.save(coach));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void softDeleteCoachById(String id) {
        Coach coach = coachRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.COACH_NOT_FOUND));

        coach.setDeleted(true);

        coachRepository.save(coach);
    }
    public Coach getCoachEntityById(String id) {
        return coachRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.COACH_NOT_FOUND));
    }
}