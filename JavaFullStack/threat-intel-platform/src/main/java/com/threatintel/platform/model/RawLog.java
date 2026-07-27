package com.threatintel.platform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Document(collection = "raw_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawLog {
    @Id
    private String id;
    private String sourceIp;
    private String destinationIp;
    private Integer targetPort;
    private String protocol;
    private Long bytesTransferred;
    private Integer failedAuthCount;
    private String httpUserAgent;
    private Instant timestamp;
    private Map<String, Object> rawPayload;
    
    @Builder.Default
    private boolean analyzed = false;
}
