package com.threatintel.platform.repository;

import com.threatintel.platform.model.RawLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RawLogRepository extends MongoRepository<RawLog, String> {
    List<RawLog> findByAnalyzedFalse();
    List<RawLog> findBySourceIp(String sourceIp);
    List<RawLog> findByTimestampBetween(Instant start, Instant end);
}
