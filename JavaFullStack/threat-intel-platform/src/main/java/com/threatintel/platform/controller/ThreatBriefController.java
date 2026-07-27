package com.threatintel.platform.controller;

import com.threatintel.platform.model.ThreatBrief;
import com.threatintel.platform.model.ThreatLevel;
import com.threatintel.platform.repository.ThreatBriefRepository;
import com.threatintel.platform.service.ThreatAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/threats")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // For development Next.js access
public class ThreatBriefController {

    private final ThreatAnalysisService threatAnalysisService;
    private final ThreatBriefRepository threatBriefRepository;

    @PostMapping("/analyze")
    public ResponseEntity<ThreatBrief> analyzePendingLogs() {
        ThreatBrief brief = threatAnalysisService.analyzePendingLogs();
        if (brief == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(brief);
    }

    @GetMapping
    public ResponseEntity<List<ThreatBrief>> getAllThreatBriefs() {
        return ResponseEntity.ok(threatBriefRepository.findAllByOrderByCreatedAtDesc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThreatBrief> getThreatBriefById(@PathVariable String id) {
        return threatBriefRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<ThreatBrief>> getThreatsByLevel(@PathVariable ThreatLevel level) {
        return ResponseEntity.ok(threatBriefRepository.findByThreatLevel(level));
    }
}
