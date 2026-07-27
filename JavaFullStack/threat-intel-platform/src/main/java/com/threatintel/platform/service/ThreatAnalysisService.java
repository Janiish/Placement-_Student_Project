package com.threatintel.platform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.threatintel.platform.model.RawLog;
import com.threatintel.platform.model.ThreatBrief;
import com.threatintel.platform.prompt.ThreatAnalysisPromptBuilder;
import com.threatintel.platform.repository.RawLogRepository;
import com.threatintel.platform.repository.ThreatBriefRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThreatAnalysisService {

    private final RawLogRepository rawLogRepository;
    private final ThreatBriefRepository threatBriefRepository;
    private final ThreatAnalysisPromptBuilder promptBuilder;
    private final ChatClient chatClient; // From Spring AI
    private final SimpMessagingTemplate messagingTemplate;
    
    public ThreatBrief analyzePendingLogs() {
        List<RawLog> pendingLogs = rawLogRepository.findByAnalyzedFalse();
        
        if (pendingLogs.isEmpty()) {
            log.info("No pending logs to analyze");
            return null;
        }
        
        log.info("Starting AI analysis for {} logs", pendingLogs.size());

        try {
            // 1. Serialize logs to JSON
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String logsJson = mapper.writeValueAsString(pendingLogs);

            // 2. Setup structured output converter for ThreatBrief
            BeanOutputConverter<ThreatBrief> converter = new BeanOutputConverter<>(ThreatBrief.class);

            // 3. Build Prompt with format instructions injected
            Prompt basePrompt = promptBuilder.buildPrompt(logsJson);
            
            // 4. Call Gemini via ChatClient
            String responseContent = chatClient.prompt(basePrompt)
                    .system(s -> s.text("{format}").param("format", converter.getFormat()))
                    .call()
                    .content();

            // 5. Parse response into ThreatBrief
            ThreatBrief threatBrief = converter.convert(responseContent);
            
            // 6. Link raw logs to the threat brief and save
            List<String> logIds = pendingLogs.stream().map(RawLog::getId).collect(Collectors.toList());
            threatBrief.setAnalyzedLogIds(logIds);
            
            ThreatBrief savedBrief = threatBriefRepository.save(threatBrief);
            
            // 7. Mark logs as analyzed
            pendingLogs.forEach(log -> log.setAnalyzed(true));
            rawLogRepository.saveAll(pendingLogs);
            
            // 8. Push real-time alert via WebSocket
            messagingTemplate.convertAndSend("/topic/threats", savedBrief);
            
            log.info("Successfully generated and saved ThreatBrief: {}", savedBrief.getId());
            return savedBrief;
            
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize logs to JSON", e);
            throw new RuntimeException("Analysis failed due to serialization error", e);
        } catch (Exception e) {
            log.error("AI Analysis failed", e);
            throw new RuntimeException("AI Analysis failed", e);
        }
    }
}
