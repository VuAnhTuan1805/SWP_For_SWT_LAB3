package com.swpteam.smokingcessation.service.impl.tracking;

import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.record.RecordHabitCreateRequest;
import com.swpteam.smokingcessation.domain.dto.record.RecordHabitResponse;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.RecordHabit;
import com.swpteam.smokingcessation.domain.entity.Streak;
import com.swpteam.smokingcessation.domain.mapper.RecordHabitMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.AccountRepository;
import com.swpteam.smokingcessation.repository.RecordHabitRepository;
import com.swpteam.smokingcessation.repository.StreakRepository;
import com.swpteam.smokingcessation.service.interfaces.identity.IAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RecordServiceImplTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    RecordHabitRepository recordHabitRepository;
    @Mock
    StreakRepository streakRepository;
    @Mock
    RecordHabitMapper recordHabitMapper;
    @Mock
    IAccountService accountService;

    @InjectMocks
    RecordHabitServiceImpl recordHabitService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRecord_firstRecordOfDay_shouldIncreaseStreak() {
        // Arrange
        String accountId = "acc1";
        LocalDate today = LocalDate.now();
        RecordHabitCreateRequest request = RecordHabitCreateRequest.builder()
                .accountId(accountId)
                .cigarettesSmoked(1)
                .date(today)
                .build();

        Account account = new Account();
        account.setId(accountId);

        RecordHabit record = new RecordHabit();
        record.setAccount(account);
        record.setDate(today);

        Streak streak = new Streak();
        streak.setStreak(3);

        RecordHabitResponse response = new RecordHabitResponse();

        when(recordHabitMapper.toEntity(request)).thenReturn(record);
        when(recordHabitRepository.save(record)).thenReturn(record);
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(accountId, today)).thenReturn(false);
        when(streakRepository.findByMember_Account_Id(accountId)).thenReturn(Optional.of(streak));
        when(streakRepository.save(streak)).thenReturn(streak);
        when(recordHabitMapper.toResponse(record)).thenReturn(response);
        when(accountService.findAccountById(accountId)).thenReturn(account);


        // Act
        RecordHabitResponse result = recordHabitService.createRecord(request);

        // Assert
        assertEquals(response, result);
        assertEquals(4, streak.getStreak());
        verify(streakRepository).save(streak);
    }

    @Test
    void createRecord_notFirstRecordOfDay_shouldNotIncreaseStreak() {
        // Arrange
        String accountId = "acc1";
        LocalDate today = LocalDate.now();
        RecordHabitCreateRequest request = RecordHabitCreateRequest.builder()
                .accountId(accountId)
                .cigarettesSmoked(1)
                .date(today)
                .build();

        Account account = new Account();
        account.setId(accountId);

        RecordHabit record = new RecordHabit();
        record.setAccount(account);
        record.setDate(today);

        Streak streak = new Streak();
        streak.setStreak(3);

        RecordHabitResponse response = new RecordHabitResponse();

        when(accountService.findAccountById(accountId)).thenReturn(account);
        when(recordHabitMapper.toEntity(request)).thenReturn(record);
        when(recordHabitRepository.save(record)).thenReturn(record);
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(accountId, today)).thenReturn(true);
        // streakRepository.findByMember_Account_Id should not be called
        when(recordHabitMapper.toResponse(record)).thenReturn(response);
        when(recordHabitRepository.findByAccountIdAndDateAndIsDeletedFalse(accountId, today))
                .thenReturn(Optional.of(record));

        // Act
        RecordHabitResponse result = recordHabitService.createRecord(request);

        // Assert
        assertEquals(response, result);
        verify(streakRepository, never()).save(any());
    }

    @Test
    void createRecord_accountNotFound_shouldThrowException() {
        // Arrange
        String accountId = "acc1";
        RecordHabitCreateRequest request = RecordHabitCreateRequest.builder()
                .accountId(accountId)
                .cigarettesSmoked(1)
                .date(LocalDate.now())
                .build();

        when(accountService.findAccountById(accountId)).thenThrow(new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        // Act & Assert
        assertThrows(AppException.class, () -> recordHabitService.createRecord(request));
    }
}