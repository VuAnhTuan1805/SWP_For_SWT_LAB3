package com.swpteam.smokingcessation.service.impl.tracking;

import com.swpteam.smokingcessation.domain.entity.Plan;
import com.swpteam.smokingcessation.domain.mapper.PhaseMapper;
import com.swpteam.smokingcessation.domain.dto.phase.PhaseRequest;
import com.swpteam.smokingcessation.domain.dto.phase.PhaseResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Phase;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.PhaseRepository;
import com.swpteam.smokingcessation.service.interfaces.tracking.IPhaseService;
import com.swpteam.smokingcessation.service.interfaces.tracking.IPlanService;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PhaseServiceImpl implements IPhaseService {

    PhaseMapper phaseMapper;
    PhaseRepository phaseRepository;

    IPlanService planService;

    @Override
    public Page<PhaseResponse> getPhasePage(PageableRequest request) {
        ValidationUtil.checkFieldExist(Phase.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Phase> phases = phaseRepository.findAllByIsDeletedFalse(pageable);

        return phases.map(phaseMapper::toResponse);
    }

    @Override
    public PhaseResponse getPhaseById(String id) {
        return phaseMapper.toResponse(findPhaseById(id));
    }

    @Override
    @Transactional
    @CachePut(value = "PHASE_CACHE", key = "#result.getId()")
    public PhaseResponse createPhase(PhaseRequest request) {
        Phase phase = phaseMapper.toEntity(request);

        Plan plan = planService.findPlanById(request.getPlanId());
        phase.setPlan(plan);

        return phaseMapper.toResponse(phaseRepository.save(phase));
    }

    @Override
    @Transactional
    @CachePut(value = "PHASE_CACHE", key = "#result.getId()")
    public PhaseResponse updatePhaseById(String id, PhaseRequest request) {
        Phase phase = findPhaseById(id);

        phaseMapper.update(phase, request);

        return phaseMapper.toResponse(phaseRepository.save(phase));
    }

    @Override
    @Transactional
    @CacheEvict(value = "PHASE_CACHE", key = "#id")
    public void softDeletePhaseById(String id) {
        Phase phase = findPhaseById(id);

        phase.setDeleted(true);
        phaseRepository.save(phase);
    }

    @Cacheable(value = "PHASE_CACHE", key = "#id")
    private Phase findPhaseById(String id) {
        Phase phase = phaseRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.PHASE_NOT_FOUND));

        if (phase.getPlan().isDeleted()) {
            throw new AppException(ErrorCode.PLAN_NOT_FOUND);
        }

        return phase;
    }
}