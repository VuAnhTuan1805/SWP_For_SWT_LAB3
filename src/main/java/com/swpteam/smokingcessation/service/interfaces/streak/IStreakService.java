package com.swpteam.smokingcessation.service.interfaces.streak;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.streak.StreakRequest;
import com.swpteam.smokingcessation.domain.dto.streak.StreakResponse;
import org.springframework.data.domain.Page;

public interface IStreakService {
    StreakResponse createStreak(String id, StreakRequest request);

    StreakResponse updateStreak(String id, StreakRequest request);

    void deleteStreak(String id);

    void resetStreak(String id);

    StreakResponse getStreakById(String id);

    Page<StreakResponse> getStreakPage(PageableRequest request);
}
