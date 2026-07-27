package com.threatintel.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;

@Document(collection = "threat_briefs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreatBrief {
    @Id
    private String id;
    private ThreatLevel threatLevel;
    private Double confidenceScore;
    private String attackTypeHypothesis;
    private MitreAttackMapping mitreAttackMapping;
    private String executiveSummary;
    private String technicalAnalysis;
    private List<String> indicatorsOfCompromise;
    private List<String> affectedNodes;
    private RemediationPlan remediationPlan;
    private List<String> analyzedLogIds;
    
    @Builder.Default
    private Instant createdAt = Instant.now();
}
