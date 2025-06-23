package com.swpteam.smokingcessation.service.interfaces.profile;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.health.HealthCreateRequest;
import com.swpteam.smokingcessation.domain.dto.health.HealthResponse;
import com.swpteam.smokingcessation.domain.dto.health.HealthUpdateRequest;
import org.springframework.data.domain.Page;

public interface IHealthService {

    Page<HealthResponse> getHealthPage(PageableRequest request);

    HealthResponse getHealthById(String id);

    Page<HealthResponse> getHealthPageByAccountId(String accountId, PageableRequest request);

    HealthResponse createHealth(HealthCreateRequest request);

    HealthResponse updateHealth(String id, HealthUpdateRequest request);

    void softDeleteHealthById(String id);
}
