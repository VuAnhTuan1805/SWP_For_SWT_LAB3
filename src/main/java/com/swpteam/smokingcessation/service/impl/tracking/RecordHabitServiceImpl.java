package com.swpteam.smokingcessation.service.impl.tracking;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.record.RecordHabitCreateRequest;
import com.swpteam.smokingcessation.domain.dto.record.RecordHabitResponse;
import com.swpteam.smokingcessation.domain.dto.record.RecordHabitUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.RecordHabit;
import com.swpteam.smokingcessation.domain.entity.Streak;
import com.swpteam.smokingcessation.domain.mapper.RecordHabitMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.RecordHabitRepository;
import com.swpteam.smokingcessation.repository.StreakRepository;
import com.swpteam.smokingcessation.service.interfaces.identity.IAccountService;
import com.swpteam.smokingcessation.service.interfaces.tracking.IRecordHabitService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecordHabitServiceImpl implements IRecordHabitService {

    RecordHabitMapper recordHabitMapper;
    RecordHabitRepository recordHabitRepository;
    StreakRepository streakRepository;

    IAccountService accountService;

    @Override
    public Page<RecordHabitResponse> getRecordPage(PageableRequest request) {
        ValidationUtil.checkFieldExist(RecordHabitMapper.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<RecordHabit> records = recordHabitRepository.findAllByIsDeletedFalse(pageable);

        return records.map(recordHabitMapper::toResponse);
    }

    @Override
    public RecordHabitResponse getRecordById(String id) {
        return recordHabitMapper.toResponse(
                recordHabitRepository.findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() -> new AppException(ErrorCode.HEALTH_RECORD_NOT_FOUND)));
    }

    @Override
    public Page<RecordHabitResponse> getRecordPageByAccountId(String accountId, PageableRequest request) {
        ValidationUtil.checkFieldExist(RecordHabitMapper.class, request.getSortBy());

        accountService.findAccountById(accountId);

        Pageable pageable = PageableRequest.getPageable(request);
        Page<RecordHabit> records = recordHabitRepository.findByAccountIdAndIsDeletedFalse(accountId, pageable);

        return records.map(recordHabitMapper::toResponse);
    }

    @Override
    @Transactional
    @CachePut(value = "RECORDHABIT_CACHE", key = "#result.getId()")
    public RecordHabitResponse createRecord(RecordHabitCreateRequest request) {
        Account account = accountService.findAccountById(request.getAccountId());

        boolean existed = recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(request.getAccountId(), request.getDate());
        if (existed) {
            RecordHabit recordHabit = recordHabitRepository.findByAccountIdAndDateAndIsDeletedFalse(request.getAccountId(), request.getDate())
                    .orElseThrow(() -> new AppException(ErrorCode.HEALTH_RECORD_NOT_FOUND));

            recordHabit.setCigarettesSmoked(request.getCigarettesSmoked());
            return recordHabitMapper.toResponse(recordHabit);
        }

        RecordHabit recordHabit = recordHabitMapper.toEntity(request);
        recordHabit.setAccount(account);
        Streak streak = streakRepository.findByMember_Account_Id(request.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.STREAK_NOT_FOUND));

        streak.setStreak(streak.getStreak() + 1);
        streakRepository.save(streak);
        return recordHabitMapper.toResponse(recordHabitRepository.save(recordHabit));
    }

    @Override
    @Transactional
    @CachePut(value = "RECORDHABIT_CACHE", key = "#result.getId()")
    public RecordHabitResponse updateRecord(String id, RecordHabitUpdateRequest request) {
        RecordHabit recordHabit = findRecordById(id);

        recordHabitMapper.update(recordHabit, request);

        return recordHabitMapper.toResponse(recordHabitRepository.save(recordHabit));
    }

    @Override
    @Transactional
    @CacheEvict(value = "RECORDHABIT_CACHE", key = "#id")
    public void softDeleteRecordById(String id) {
        RecordHabit recordHabit = findRecordById(id);

        recordHabit.setDeleted(true);
        recordHabitRepository.save(recordHabit);
    }

    @Cacheable(value = "RECORDHABIT_CACHE", key = "#id")
    private RecordHabit findRecordById(String id) {
        RecordHabit recordHabit = recordHabitRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTH_RECORD_NOT_FOUND));

        if (recordHabit.getAccount().isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        return recordHabit;
    }
}