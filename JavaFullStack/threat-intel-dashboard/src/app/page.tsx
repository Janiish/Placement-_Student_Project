import ThreatFeed from '@/components/ThreatFeed';

export default function DashboardPage() {
  return (
    <main className="dashboard-container">
      <header>
        <h1 className="gradient-text">SOC Operations Center</h1>
        <p>Real-time Distributed Threat Intelligence</p>
      </header>
      
      <div className="dashboard-grid">
        {/* System Status Panel */}
        <div className="glass-panel">
          <div className="card-title">
            <span>System Status</span>
            <span className="badge badge-low">Healthy</span>
          </div>
          <div className="data-list">
            <div className="data-item">Backend API: ONLINE (ws://localhost:8080)</div>
            <div className="data-item">MongoDB Cluster: CONNECTED</div>
            <div className="data-item">Spring AI / Gemini: READY</div>
            <div className="data-item">Logs Ingested (1hr): 1,402</div>
          </div>
        </div>

        {/* Global Mitigation Status */}
        <div className="glass-panel">
          <div className="card-title">
            <span>Active Mitigations</span>
            <span className="badge badge-high pulse-critical">WAF Active</span>
          </div>
          <p className="card-subtitle">Recent automated responses to high-confidence threats.</p>
          <div className="data-list">
            <div className="data-item" style={{ borderLeftColor: 'var(--critical)' }}>
              BLOCKED: 192.168.1.100 (SSH Brute Force)
            </div>
            <div className="data-item" style={{ borderLeftColor: 'var(--high)' }}>
              RATE LIMITED: 10.0.0.50 (SQLi Pattern)
            </div>
          </div>
        </div>
      </div>

      {/* Threat Feed Component (Client Side) */}
      <ThreatFeed />
      
    </main>
  );
}
