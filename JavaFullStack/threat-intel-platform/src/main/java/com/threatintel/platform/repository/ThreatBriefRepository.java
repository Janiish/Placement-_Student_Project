package com.threatintel.platform.repository;

import com.threatintel.platform.model.ThreatBrief;
import com.threatintel.platform.model.ThreatLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreatBriefRepository extends MongoRepository<ThreatBrief, String> {
    List<ThreatBrief> findByThreatLevel(ThreatLevel level);
    List<ThreatBrief> findAllByOrderByCreatedAtDesc();
}
