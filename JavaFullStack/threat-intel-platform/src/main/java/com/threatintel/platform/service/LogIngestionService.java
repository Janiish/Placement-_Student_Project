package com.threatintel.platform.service;

import com.threatintel.platform.model.RawLog;
import com.threatintel.platform.repository.RawLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogIngestionService {

    private final RawLogRepository rawLogRepository;

    public List<RawLog> ingestLogs(List<RawLog> logs) {
        log.info("Ingesting {} raw logs", logs.size());
        
        // Ensure timestamps are set if missing
        List<RawLog> processedLogs = logs.stream().peek(logEntry -> {
            if (logEntry.getTimestamp() == null) {
                logEntry.setTimestamp(Instant.now());
            }
            logEntry.setAnalyzed(false); // Force to false on ingestion
        }).collect(Collectors.toList());

        List<RawLog> savedLogs = rawLogRepository.saveAll(processedLogs);
        log.info("Successfully ingested {} logs", savedLogs.size());
        
        return savedLogs;
    }

    public List<RawLog> getAllLogs() {
        return rawLogRepository.findAll();
    }

    public RawLog getLogById(String id) {
        return rawLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Log not found with id: " + id));
    }
}
