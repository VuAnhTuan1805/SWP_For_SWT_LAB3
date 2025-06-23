package com.swpteam.smokingcessation.domain.dto.plan;

import lombok.Data;
import java.util.List;

@Data
public class PlanTemplateWrapper {
    private List<PlanTemplateResponse> levels;
}
