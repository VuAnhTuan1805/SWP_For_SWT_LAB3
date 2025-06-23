package com.swpteam.smokingcessation.schedule;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.entity.Member;
import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.domain.entity.Streak;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.RecordHabitRepository;
import com.swpteam.smokingcessation.repository.SettingRepository;
import com.swpteam.smokingcessation.repository.StreakRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StreakSchedulerTest {

    @Mock
    SettingRepository settingRepository;
    @Mock
    RecordHabitRepository recordHabitRepository;
    @Mock
    StreakRepository streakRepository;

    @InjectMocks
    StreakScheduler streakScheduler;

    Setting setting;
    Account account;
    Streak streak;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setId("account1");

        setting = new Setting();
        setting.setAccount(account);
        setting.setReportDeadline(LocalTime.now().minusMinutes(1)); // Deadline already passed

        streak = new Streak();
        streak.setStreak(5);

        Member member = new Member();
        member.setHighestStreak(3);
        streak.setMember(member);
    }

    @Test
    void shouldResetStreakWhenNoRecordAfterDeadline() {
        when(settingRepository.findAllByIsDeletedFalse()).thenReturn(List.of(setting));
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(anyString(), any(LocalDate.class))).thenReturn(false);
        when(streakRepository.findByMember_Account_Id(anyString())).thenReturn(Optional.of(streak));

        streakScheduler.checkAndResetStreak();

        verify(streakRepository).save(streak);
        assertEquals(0, streak.getStreak());
    }

    @Test
    void shouldNotResetStreakWhenRecordExists() {
        when(settingRepository.findAllByIsDeletedFalse()).thenReturn(List.of(setting));
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(anyString(), any(LocalDate.class))).thenReturn(true);
        when(streakRepository.findByMember_Account_Id(anyString())).thenReturn(Optional.of(streak));

        streakScheduler.checkAndResetStreak();

        verify(streakRepository, never()).save(any());
        assertEquals(5, streak.getStreak());
    }

    @Test
    void shouldNotResetStreakWhenStreakIsZero() {
        streak.setStreak(0);
        when(settingRepository.findAllByIsDeletedFalse()).thenReturn(List.of(setting));
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(anyString(), any(LocalDate.class))).thenReturn(false);
        when(streakRepository.findByMember_Account_Id(anyString())).thenReturn(Optional.of(streak));

        streakScheduler.checkAndResetStreak();

        verify(streakRepository, never()).save(any());
        assertEquals(0, streak.getStreak());
    }

    @Test
    void shouldThrowAppExceptionWhenRecordRepositoryFails() {
        when(settingRepository.findAllByIsDeletedFalse()).thenReturn(List.of(setting));
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(anyString(), any(LocalDate.class)))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(AppException.class, () -> streakScheduler.checkAndResetStreak());
    }

    @Test
    void shouldNotResetWhenDeadlineNotPassed() {
        setting.setReportDeadline(LocalTime.now().plusMinutes(5)); // Future

        when(settingRepository.findAllByIsDeletedFalse()).thenReturn(List.of(setting));

        streakScheduler.checkAndResetStreak();

        verifyNoInteractions(recordHabitRepository);
        verifyNoInteractions(streakRepository);
    }

    @Test
    void shouldThrowAppExceptionWhenRepositoryThrowsException() {
        when(settingRepository.findAllByIsDeletedFalse()).thenReturn(List.of(setting));
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(anyString(), any(LocalDate.class)))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(AppException.class, () -> streakScheduler.checkAndResetStreak());
    }

    @Test
    void shouldUpdateHighestStreakAndResetStreak() {
        when(settingRepository.findAllByIsDeletedFalse()).thenReturn(List.of(setting));
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(anyString(), any(LocalDate.class))).thenReturn(false);
        when(streakRepository.findByMember_Account_Id(anyString())).thenReturn(Optional.of(streak));

        streakScheduler.checkAndResetStreak();

        verify(streakRepository).save(streak);
        assertEquals(0, streak.getStreak());
        assertEquals(5, streak.getMember().getHighestStreak());
    }

    @Test
    void shouldNotUpdateHighestStreakIfAlreadyHigher() {
        streak.getMember().setHighestStreak(10); // already higher
        when(settingRepository.findAllByIsDeletedFalse()).thenReturn(List.of(setting));
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(anyString(), any(LocalDate.class))).thenReturn(false);
        when(streakRepository.findByMember_Account_Id(anyString())).thenReturn(Optional.of(streak));

        streakScheduler.checkAndResetStreak();

        verify(streakRepository).save(streak);
        assertEquals(0, streak.getStreak());
        assertEquals(10, streak.getMember().getHighestStreak());
    }

    @Test
    void shouldDoNothingWhenStreakNotFound() {
        when(settingRepository.findAllByIsDeletedFalse()).thenReturn(List.of(setting));
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(anyString(), any(LocalDate.class))).thenReturn(false);
        when(streakRepository.findByMember_Account_Id(anyString())).thenReturn(Optional.empty());

        streakScheduler.checkAndResetStreak();

        verify(streakRepository, never()).save(any());
    }

    @Test
    void shouldThrowAppExceptionWhenStreakHasNoMember() {
        streak.setMember(null); // simulate broken data
        when(settingRepository.findAllByIsDeletedFalse()).thenReturn(List.of(setting));
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(anyString(), any(LocalDate.class))).thenReturn(false);
        when(streakRepository.findByMember_Account_Id(anyString())).thenReturn(Optional.of(streak));

        assertThrows(AppException.class, () -> streakScheduler.checkAndResetStreak());
    }

    @Test
    void shouldProcessMultipleSettingsIndependently() {
        Setting setting2 = new Setting();
        Account account2 = new Account();
        account2.setId("account2");
        setting2.setAccount(account2);
        setting2.setReportDeadline(LocalTime.now().minusMinutes(1));

        Streak streak2 = new Streak();
        streak2.setStreak(4);
        Member member2 = new Member();
        member2.setHighestStreak(2);
        streak2.setMember(member2);

        when(settingRepository.findAllByIsDeletedFalse()).thenReturn(List.of(setting, setting2));
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(eq("account1"), any())).thenReturn(false);
        when(recordHabitRepository.existsByAccountIdAndDateAndIsDeletedFalse(eq("account2"), any())).thenReturn(false);
        when(streakRepository.findByMember_Account_Id(eq("account1"))).thenReturn(Optional.of(streak));
        when(streakRepository.findByMember_Account_Id(eq("account2"))).thenReturn(Optional.of(streak2));

        streakScheduler.checkAndResetStreak();

        verify(streakRepository, times(2)).save(any());
        assertEquals(0, streak.getStreak());
        assertEquals(0, streak2.getStreak());
    }
}