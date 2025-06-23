package com.swpteam.smokingcessation.service.impl.profile;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.dto.achievement.*;
import com.swpteam.smokingcessation.domain.entity.Achievement;
import com.swpteam.smokingcessation.domain.mapper.AchievementMapper;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.AchievementRepository;
import com.swpteam.smokingcessation.service.interfaces.profile.IAchievementService;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AchievementServiceImpl implements IAchievementService {

    AchievementRepository achievementRepository;
    AchievementMapper achievementMapper;

    @Override
    public Page<AchievementResponse> getAchievementPage(PageableRequest request) {
        ValidationUtil.checkFieldExist(Achievement.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Achievement> achievements = achievementRepository.findAllByIsDeletedFalse(pageable);

        return achievements.map(achievementMapper::toResponse);
    }

    @Override
    public AchievementResponse getAchievementByName(String name) {
        return achievementMapper.toResponse(findAchievementByName(name));
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CachePut(value = "ACHIEVEMENT_CACHE", key = "#result.getName()")
    public AchievementResponse createAchievement(AchievementCreateRequest request) {
        if (achievementRepository.existsByNameAndIsDeletedFalse(request.getName())) {
            throw new AppException(ErrorCode.ACHIEVEMENT_ALREADY_EXISTS);
        }

        Achievement achievement = achievementMapper.toEntity(request);
        return achievementMapper.toResponse(achievementRepository.save(achievement));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CachePut(value = "ACHIEVEMENT_CACHE", key = "#name")
    public AchievementResponse updateAchievement(String name, AchievementUpdateRequest request) {
        Achievement achievement = findAchievementByName(name);

        achievementMapper.update(achievement, request);

        return achievementMapper.toResponse(achievementRepository.save(achievement));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "ACHIEVEMENT_CACHE", key = "#name")
    public void softDeleteAchievement(String name) {
        Achievement achievement = findAchievementByName(name);
        achievement.setDeleted(true);
        achievementRepository.save(achievement);
    }

    @Override
    @Transactional
    @Cacheable(value = "ACHIEVEMENT_CACHE", key = "#name")
    public Achievement findAchievementByName(String name) {
        return achievementRepository.findByNameAndIsDeletedFalse(name)
                .orElseThrow(() -> new AppException(ErrorCode.ACHIEVEMENT_NOT_FOUND));
    }

}
