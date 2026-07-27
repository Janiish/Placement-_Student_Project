package com.threatintel.platform.controller;

import com.threatintel.platform.model.RawLog;
import com.threatintel.platform.service.LogIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // For development Next.js access
public class LogController {

    private final LogIngestionService logIngestionService;

    @PostMapping
    public ResponseEntity<List<RawLog>> ingestLogs(@RequestBody List<RawLog> logs) {
        List<RawLog> ingestedLogs = logIngestionService.ingestLogs(logs);
        return new ResponseEntity<>(ingestedLogs, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RawLog>> getAllLogs() {
        return ResponseEntity.ok(logIngestionService.getAllLogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawLog> getLogById(@PathVariable String id) {
        return ResponseEntity.ok(logIngestionService.getLogById(id));
    }
}
