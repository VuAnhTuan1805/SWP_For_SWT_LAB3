package com.swpteam.smokingcessation.service.interfaces.profile;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.coach.CoachRequest;
import com.swpteam.smokingcessation.domain.dto.coach.CoachResponse;
import org.springframework.data.domain.Page;

public interface ICoachService {
    Page<CoachResponse> getCoachPage(PageableRequest request);

    CoachResponse getCoachById(String id);

    CoachResponse createCoach(CoachRequest request);

    CoachResponse updateCoachById(String id, CoachRequest request);
    
}