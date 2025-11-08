type Risk = {
  perEntityMax: number
  documentScore: number
  categoryScores?: Record<string, number>
}

export default function RiskCard({ risk }: { risk?: Risk }) {
  const score = Math.round((risk?.documentScore ?? 0) * 100)
  const level = score > 66 ? 'High' : score > 33 ? 'Medium' : 'Low'

  const categories = risk?.categoryScores ?? {}
  const entries = Object.entries(categories)

  return (
    <div className="card">
      <div className="text-sm opacity-70 mb-1">Risk Score</div>
      <div className="kpi">{score}</div>
      <div className="badge mt-2">{level}</div>
      <div className="mt-3 text-xs opacity-70">
        per-entity max: {risk?.perEntityMax?.toFixed(2) ?? '0.00'}
      </div>

      {entries.length > 0 && (
        <div className="mt-3 text-xs opacity-80 space-y-1">
          {entries.map(([cat, val]) => (
            <div key={cat}>
              <span className="badge mr-1">{cat}</span>
              {(val ?? 0).toFixed(2)}
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
