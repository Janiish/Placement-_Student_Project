package com.threatintel.platform.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemediationPlan {
    private String immediateAction;
    private String longTermRecommendation;
}
