export default function RiskCard({risk}:{risk?:{perEntityMax:number, documentScore:number}}){
  const score = Math.round((risk?.documentScore ?? 0)*100)
  const level = score>66?'High':score>33?'Medium':'Low'
  return (
    <div className="card">
      <div className="text-sm opacity-70 mb-1">Risk Score</div>
      <div className="kpi">{score}</div>
      <div className="badge mt-2">{level}</div>
      <div className="mt-3 text-xs opacity-70">per-entity max: {risk?.perEntityMax?.toFixed(2) ?? '0.00'}</div>
    </div>
  )
}
