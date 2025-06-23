package com.swpteam.smokingcessation.service.impl.profile;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.coach.CoachRequest;
import com.swpteam.smokingcessation.domain.dto.coach.CoachResponse;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Coach;
import com.swpteam.smokingcessation.domain.mapper.CoachMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.CoachRepository;
import com.swpteam.smokingcessation.service.interfaces.identity.IAccountService;
import com.swpteam.smokingcessation.service.interfaces.profile.ICoachService;
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
public class CoachServiceImpl implements ICoachService {

    CoachRepository coachRepository;
    CoachMapper coachMapper;

    IAccountService accountService;

    @Override
    public Page<CoachResponse> getCoachPage(PageableRequest request) {
        ValidationUtil.checkFieldExist(Coach.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Coach> coaches = coachRepository.findAllByIsDeletedFalse(pageable);

        return coaches.map(coachMapper::toResponse);
    }

    @Override
    public CoachResponse getCoachById(String id) {
        return coachMapper.toResponse(findCoachById(id));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @CachePut(value = "COACH_CACHE", key = "#result.getId()")
    public CoachResponse createCoach(CoachRequest request) {
        Coach coach = coachMapper.toEntity(request);

        Account account = accountService.findAccountById(request.getAccountId());
        coach.setAccount(account);

        return coachMapper.toResponse(coachRepository.save(coach));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH)")
    @CachePut(value = "COACH_CACHE", key = "#result.getId()")
    public CoachResponse updateCoachById(String id, CoachRequest request) {
        Coach coach = findCoachById(id);

        coachMapper.update(coach, request);

        return coachMapper.toResponse(coachRepository.save(coach));
    }

    @Cacheable(value = "COACH_CACHE", key = "#id")
    private Coach findCoachById(String id) {
        Coach coach = coachRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.COACH_NOT_FOUND));

        if (coach.getAccount().isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_DELETED);
        }

        return coach;
    }

}