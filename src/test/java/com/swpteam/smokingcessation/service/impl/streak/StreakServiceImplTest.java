package com.swpteam.smokingcessation.service.impl.streak;

import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Member;
import com.swpteam.smokingcessation.domain.entity.Streak;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.MemberRepository;
import com.swpteam.smokingcessation.repository.StreakRepository;
import com.swpteam.smokingcessation.utils.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class StreakServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    StreakRepository streakRepository;
    @Mock
    AuthUtil authUtil;

    @InjectMocks
    StreakServiceImpl streakService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void resetStreak_shouldResetAndUpdateHighestStreak_whenAllowedAndStreakIsHigher() {
        // Arrange
        Member member = new Member();
        member.setId("member1");
        member.setHighestStreak(3);

        Streak streak = new Streak();
        streak.setStreak(5);
        streak.setMember(member);

        when(streakRepository.findById(member.getId())).thenReturn(Optional.of(streak));
        when(authUtil.isAdminOrOwner(member.getId())).thenReturn(true);

        // Act
        streakService.resetStreak(member.getId());

        // Assert
        assertEquals(5, member.getHighestStreak());
        assertEquals(0, streak.getStreak());
    }

    @Test
    void resetStreak_shouldResetButNotUpdateHighestStreak_whenAllowedAndHighestIsHigher() {
        // Arrange
        Member member = new Member();
        member.setId("member2");
        member.setHighestStreak(10);

        Streak streak = new Streak();
        streak.setStreak(5);
        streak.setMember(member);

        when(streakRepository.findById(member.getId())).thenReturn(Optional.of(streak));
        when(authUtil.isAdminOrOwner(member.getId())).thenReturn(true);

        // Act
        streakService.resetStreak(member.getId());

        // Assert
        assertEquals(10, member.getHighestStreak());
        assertEquals(0, streak.getStreak());
    }

    @Test
    void resetStreak_shouldThrowException_whenNotAllowed() {
        // Arrange
        Member member = new Member();
        member.setId("member3");
        member.setHighestStreak(2);

        Streak streak = new Streak();
        streak.setStreak(4);
        streak.setMember(member);

        when(streakRepository.findById(member.getId())).thenReturn(Optional.of(streak));
        when(authUtil.isAdminOrOwner(member.getId())).thenReturn(false);

        // Act & Assert
        AppException ex = assertThrows(AppException.class, () -> streakService.resetStreak(member.getId()));
        System.out.println("Caught exception: " + ex.getErrorCode());
        assertEquals(ErrorCode.OTHERS_STREAK_CANNOT_BE_DELETED, ex.getErrorCode());
    }

    @Test
    void resetStreak_shouldThrowException_whenStreakNotFound() {
        // Arrange
        String streakId = "notfound";
        when(streakRepository.findById(streakId)).thenReturn(Optional.empty());

        // Act & Assert
        AppException ex = assertThrows(AppException.class, () -> streakService.resetStreak(streakId));
        assertEquals(ErrorCode.STREAK_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    void resetStreak_shouldUpdateMemberHighestStreak_whenCurrentStreakIsHigher() {
        // Arrange
        Member member = new Member();
        member.setId("member123");
        member.setHighestStreak(2);

        Streak streak = new Streak();
        streak.setStreak(5);
        streak.setMember(member);

        when(streakRepository.findById(member.getId())).thenReturn(Optional.of(streak));
        when(authUtil.isAdminOrOwner(member.getId())).thenReturn(true);

        // Act
        streakService.resetStreak(member.getId());

        // Assert
        assertEquals(5, member.getHighestStreak(), "Member's highestStreak should be updated to current streak");
        assertEquals(0, streak.getStreak(), "Streak should be reset to 0");
    }
}