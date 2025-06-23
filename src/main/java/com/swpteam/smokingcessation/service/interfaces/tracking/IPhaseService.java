package com.swpteam.smokingcessation.service.interfaces.tracking;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.phase.PhaseRequest;
import com.swpteam.smokingcessation.domain.dto.phase.PhaseResponse;
import org.springframework.data.domain.Page;

public interface IPhaseService {
    Page<PhaseResponse> getPhasePage(PageableRequest request);

    PhaseResponse getPhaseById(String id);

    PhaseResponse createPhase(PhaseRequest request);

    PhaseResponse updatePhaseById(String id, PhaseRequest request);

    void softDeletePhaseById(String id);
}