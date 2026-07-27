package com.threatintel.platform.prompt;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ThreatAnalysisPromptBuilder {

    private static final String SYSTEM_PROMPT = """
            You are an elite Level 3 Cybersecurity Incident Response Analyst. Your task is to analyze raw network log data and generate a concise, actionable Threat Intelligence Brief.
            
            STRICT CONSTRAINTS:
            - DO NOT hallucinate or invent data. Base your analysis ONLY on the provided JSON logs.
            - Maintain a highly technical, objective, and urgent tone.
            - You must output your response in strict JSON format matching the schema provided by the system. Do not include markdown formatting like ```json in the output.
            """;

    private static final String USER_PROMPT_TEMPLATE = """
            Analyze the following aggregated anomaly logs and network telemetry. Your objective is to identify potential unauthorized access attempts, lateral movement, or data exfiltration. Correlate the provided events and cross-reference anomalous patterns against known threat actor methodologies.
            
            RAW_LOG_DATA:
            {raw_logs}
            
            OUTPUT_SCHEMA:
            You must strictly map your analysis to the required JSON structure.
            """;

    public Prompt buildPrompt(String rawLogsJson) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(SYSTEM_PROMPT);
        Message systemMessage = systemPromptTemplate.createMessage();
        
        // Replaces {raw_logs} with the actual JSON string of logs
        String userMessageContent = USER_PROMPT_TEMPLATE.replace("{raw_logs}", rawLogsJson);
        UserMessage userMessage = new UserMessage(userMessageContent);
        
        return new Prompt(List.of(systemMessage, userMessage));
    }
}
