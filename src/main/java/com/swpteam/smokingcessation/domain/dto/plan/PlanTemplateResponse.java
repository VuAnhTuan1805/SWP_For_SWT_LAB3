package com.swpteam.smokingcessation.domain.dto.plan;

import lombok.Data;
import java.util.List;

@Data
public class PlanTemplateResponse {
    private int level;
    private List<PhaseTemplateResponse> plan;
}
