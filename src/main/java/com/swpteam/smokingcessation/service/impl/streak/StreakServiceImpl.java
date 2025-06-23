package com.swpteam.smokingcessation.service.impl.streak;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.streak.StreakRequest;
import com.swpteam.smokingcessation.domain.dto.streak.StreakResponse;
import com.swpteam.smokingcessation.domain.entity.Member;
import com.swpteam.smokingcessation.domain.entity.Streak;
import com.swpteam.smokingcessation.domain.mapper.StreakMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.MemberRepository;
import com.swpteam.smokingcessation.repository.StreakRepository;
import com.swpteam.smokingcessation.service.interfaces.streak.IStreakService;
import com.swpteam.smokingcessation.utils.AuthUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StreakServiceImpl implements IStreakService {
    StreakMapper streakMapper;
    StreakRepository streakRepository;
    MemberRepository memberRepository;
    AuthUtil authUtil;

    @Override
    @Transactional
    public StreakResponse createStreak(String id, StreakRequest request) {
        if (streakRepository.findById(id).isPresent()) {
            throw new AppException(ErrorCode.STREAK_ALREADY_EXISTS);
        }

        Streak streak = streakMapper.toEntity(request);

        Member member = memberRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));
        streak.setMember(member);
        return streakMapper.toResponse(streakRepository.save(streak));
    }

    @Override
    @Transactional
    public StreakResponse updateStreak(String id, StreakRequest request) {
        Streak streak = streakRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STREAK_NOT_FOUND));
        streakMapper.update(streak, request);
        return streakMapper.toResponse(streakRepository.save(streak));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    @Transactional
    public void deleteStreak(String id) {
        Streak streak = streakRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STREAK_NOT_FOUND));
        streakRepository.delete(streak);
    }

    @Override
    public void resetStreak(String id) {
        Streak streak = streakRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STREAK_NOT_FOUND));
        Member member = streak.getMember();
        boolean isAllowed = authUtil.isAdminOrOwner(member.getId());

        if (!isAllowed) {
            throw new AppException(ErrorCode.OTHERS_STREAK_CANNOT_BE_DELETED);
        }

        if (member.getHighestStreak() < streak.getStreak()) {
            member.setHighestStreak(streak.getStreak());
        }
        streak.setStreak(0);

        memberRepository.save(member);
        streakRepository.save(streak);
    }

    @Override
    public StreakResponse getStreakById(String id) {
        Streak streak = streakRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STREAK_NOT_FOUND));
        return streakMapper.toResponse(streak);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Page<StreakResponse> getStreakPage(PageableRequest request) {
        Pageable pageable = PageableRequest.getPageable(request);
        return streakRepository.findAll(pageable).map(streakMapper::toResponse);
    }
}
