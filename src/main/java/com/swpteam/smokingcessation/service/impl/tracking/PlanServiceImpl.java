package com.swpteam.smokingcessation.service.impl.tracking;

import com.swpteam.smokingcessation.domain.dto.phase.PhaseResponse;
import com.swpteam.smokingcessation.domain.dto.plan.PhaseTemplateResponse;
import com.swpteam.smokingcessation.domain.dto.plan.PlanTemplateResponse;
import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.mapper.PlanMapper;
import com.swpteam.smokingcessation.domain.dto.plan.PlanRequest;
import com.swpteam.smokingcessation.domain.dto.plan.PlanResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Plan;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.repository.PlanRepository;
import com.swpteam.smokingcessation.service.interfaces.identity.IAccountService;
import com.swpteam.smokingcessation.service.interfaces.tracking.IPlanService;
import com.swpteam.smokingcessation.utils.FileLoaderUtil;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PlanServiceImpl implements IPlanService {

    PlanMapper planMapper;
    PlanRepository planRepository;

    FileLoaderUtil fileLoaderUtil;
    IAccountService accountService;

    @Override
    public Page<PlanResponse> getPlanPage(PageableRequest request) {
        ValidationUtil.checkFieldExist(Plan.class, request.getSortBy());

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Plan> plans = planRepository.findAllByIsDeletedFalse(pageable);

        return plans.map(planMapper::toResponse);
    }

    @Override
    public PlanResponse getPlanById(String id) {
        return planMapper.toResponse(findPlanById(id));
    }

    @Override
    @Transactional
    @CachePut(value = "PLAN_CACHE", key = "#result.getId()")
    public PlanResponse createPlan(PlanRequest request) {
        Plan plan = planMapper.toEntity(request);

        Account account = accountService.findAccountById(request.getAccountId());
        plan.setAccount(account);

        return planMapper.toResponse(planRepository.save(plan));
    }

    @Override
    @Transactional
    @CachePut(value = "PLAN_CACHE", key = "#result.getId()")
    public PlanResponse updatePlanById(String id, PlanRequest request) {
        Plan plan = findPlanById(id);

        planMapper.update(plan, request);

        return planMapper.toResponse(planRepository.save(plan));
    }

    @Override
    @CachePut(value = "PLAN_CACHE", key = "#result.getId()")
    public PlanResponse getPlanByFtndScore(int ftndScore) {
        if (ftndScore < 0 || ftndScore > 10) {
            log.error("Invalid FTND score: {}", ftndScore);
            throw new AppException(ErrorCode.INVALID_FTND_SCORE);
        }

        int level = mapFtndScoreToLevel(ftndScore);
        List<PlanTemplateResponse> templates = fileLoaderUtil.loadPlanTemplate("quitplan/template-plan.json");

        PlanTemplateResponse selectedPlan = templates.stream()
                .filter(t -> t.getLevel() == level)
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_NOT_FOUND));

        LocalDate planStartDate = LocalDate.now();
        LocalDate currentPhaseStartDate = planStartDate;
        List<PhaseResponse> phases = new ArrayList<>();

        for (PhaseTemplateResponse phase : selectedPlan.getPlan()) {
            LocalDate phaseEndDate = currentPhaseStartDate.plusDays(6); // mỗi phase kéo dài 7 ngày

            PhaseResponse response = PhaseResponse.builder()
                    .phase(phase.getPhase())
                    .cigaretteBound((phase.getCigaretteBound()))
                    .startDate(currentPhaseStartDate)
                    .endDate(phaseEndDate)
                    .build();
            phases.add(response);
            currentPhaseStartDate = phaseEndDate.plusDays(1);
        }

        LocalDate planEndDate = phases.getLast().getEndDate();

        return PlanResponse.builder()
                .planName("Default plan for level " + selectedPlan.getLevel())
                .description("This plan is generated based on your FTND score")
                .startDate(planStartDate)
                .endDate(planEndDate)
                .phases(phases)
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "PLAN_CACHE", key = "#id")
    public void softDeletePlanById(String id) {
        Plan plan = findPlanById(id);

        plan.setDeleted(true);
        planRepository.save(plan);
    }

    @Override
    @Cacheable(value = "PLAN_CACHE", key = "#id")
    public Plan findPlanById(String id) {
        Plan plan = planRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_NOT_FOUND));

        if (plan.getAccount().isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        return plan;
    }

    private int mapFtndScoreToLevel(int ftnd) {
        if (ftnd < 3) return 1;
        else if (ftnd <= 4) return 2;
        else if (ftnd <= 5) return 3;
        else if (ftnd <= 7) return 4;
        else return 5;
    }
}