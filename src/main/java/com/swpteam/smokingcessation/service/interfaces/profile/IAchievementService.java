package com.swpteam.smokingcessation.service.interfaces.profile;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.achievement.AchievementCreateRequest;
import com.swpteam.smokingcessation.domain.dto.achievement.AchievementResponse;
import com.swpteam.smokingcessation.domain.dto.achievement.AchievementUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Achievement;
import org.springframework.data.domain.Page;

public interface IAchievementService {
    Page<AchievementResponse> getAchievementPage(PageableRequest request);

    AchievementResponse getAchievementByName(String name);

    AchievementResponse createAchievement(AchievementCreateRequest request);

    AchievementResponse updateAchievement(String name, AchievementUpdateRequest request);

    void softDeleteAchievement(String name);

    Achievement findAchievementByName(String name);
}
