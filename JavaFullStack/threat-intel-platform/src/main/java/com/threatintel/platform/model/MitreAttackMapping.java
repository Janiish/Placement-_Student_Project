package com.threatintel.platform.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MitreAttackMapping {
    private String tactic;
    private String techniqueId;
}
