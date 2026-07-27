'use client';

import { useState, useEffect } from 'react';

// Matches the backend ThreatBrief.java model
interface ThreatBrief {
  id: string;
  threatLevel: 'CRITICAL' | 'HIGH' | 'MEDIUM' | 'LOW';
  confidenceScore: number;
  attackTypeHypothesis: string;
  mitreAttackMapping: { tactic: string; techniqueId: string };
  executiveSummary: string;
  technicalAnalysis: string;
  indicatorsOfCompromise: string[];
  affectedNodes: string[];
  remediationPlan: { immediateAction: string; longTermRecommendation: string };
  createdAt: string;
}

const MOCK_THREATS: ThreatBrief[] = [
  {
    id: "tb-10294",
    threatLevel: "CRITICAL",
    confidenceScore: 0.94,
    attackTypeHypothesis: "Distributed SSH Brute Force and Lateral Movement",
    mitreAttackMapping: { tactic: "Credential Access", techniqueId: "T1110" },
    executiveSummary: "Multiple external nodes have systematically attempted to bypass authentication on edge servers, followed by signs of internal reconnaissance.",
    technicalAnalysis: "Analysis of 400+ logs reveals a coordinated password spraying attack targeting port 22 across the DMZ. Following successful access on 10.0.0.4, anomalous traffic spiked toward internal database subnets.",
    indicatorsOfCompromise: ["192.168.1.100", "203.0.113.45"],
    affectedNodes: ["10.0.0.4", "db-cluster-01"],
    remediationPlan: {
      immediateAction: "Block IOC IPs at the edge firewall and isolate 10.0.0.4 from the DB subnet.",
      longTermRecommendation: "Enforce MFA for all SSH access and implement strict rate limiting."
    },
    createdAt: new Date().toISOString()
  }
];

export default function ThreatFeed() {
  const [threats, setThreats] = useState<ThreatBrief[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Simulate initial load from REST API
    const timer = setTimeout(() => {
      setThreats(MOCK_THREATS);
      setLoading(false);
    }, 1500);
    return () => clearTimeout(timer);
  }, []);

  return (
    <div style={{ marginTop: '24px' }}>
      <h2 className="card-title" style={{ fontSize: '1.8rem' }}>Live Threat Intelligence Feed</h2>
      <p className="card-subtitle" style={{ marginBottom: '24px' }}>
        Awaiting real-time STOMP WebSocket messages from Spring AI...
      </p>

      {loading ? (
        <div className="glass-panel skeleton" style={{ height: '300px' }}></div>
      ) : (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
          {threats.map((threat) => (
            <div key={threat.id} className="glass-panel" style={{ borderLeft: `4px solid var(--${threat.threatLevel.toLowerCase()})` }}>
              <div className="card-title">
                <span>{threat.attackTypeHypothesis}</span>
                <span className={`badge badge-${threat.threatLevel.toLowerCase()} pulse-${threat.threatLevel.toLowerCase()}`}>
                  {threat.threatLevel} ({Math.round(threat.confidenceScore * 100)}%)
                </span>
              </div>
              
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '24px', marginTop: '16px' }}>
                <div>
                  <h4 style={{ color: 'var(--text-secondary)', marginBottom: '8px', fontSize: '0.9rem' }}>Executive Summary</h4>
                  <p style={{ lineHeight: '1.6' }}>{threat.executiveSummary}</p>
                  
                  <div style={{ marginTop: '16px' }}>
                    <span className="badge" style={{ background: 'rgba(255,255,255,0.1)' }}>
                      MITRE: {threat.mitreAttackMapping.tactic} ({threat.mitreAttackMapping.techniqueId})
                    </span>
                  </div>
                </div>

                <div>
                  <h4 style={{ color: 'var(--text-secondary)', marginBottom: '8px', fontSize: '0.9rem' }}>Remediation (Auto-Generated)</h4>
                  <div className="data-list">
                    <div className="data-item">
                      <strong>Immediate:</strong> {threat.remediationPlan.immediateAction}
                    </div>
                    <div className="data-item">
                      <strong>Strategic:</strong> {threat.remediationPlan.longTermRecommendation}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
