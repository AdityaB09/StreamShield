import Header from './components/Header'
import LiveDemo from './components/LiveDemo'
import AuditTable from './components/AuditTable'

export default function App(){
  return (
    <div className="max-w-6xl mx-auto p-6">
      <Header/>
      <section className="grid grid-cols-1 lg:grid-cols-2 gap-4 mb-6">
        <div className="card">
          <h2 className="text-xl font-extrabold mb-2">What it does</h2>
          <p className="opacity-80 text-sm">
            Real-time hybrid detection for PII/PHI/PCI + context-aware redaction/masking/synthesis, 
            with per-entity risk and compliance-ready audit trails. Java-native, low-latency, on-prem friendly.
          </p>
        </div>
        <div className="card">
          <h2 className="text-xl font-extrabold mb-2">Stack</h2>
          <ul className="text-sm grid grid-cols-2 gap-2 opacity-80">
            <li>Java 21</li><li>Spring Boot 3 (WebFlux)</li>
            <li>DJL + HF NER</li><li>OpenNLP fallback</li>
            <li>Lucene + regex</li><li>PostgreSQL (audit)</li>
          </ul>
        </div>
      </section>

      <LiveDemo/>

      <div className="mt-6">
        <AuditTable/>
      </div>
    </div>
  )
}
