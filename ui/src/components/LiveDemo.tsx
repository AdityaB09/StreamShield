import React from 'react'
import { api } from '../lib/api'
import RiskCard from './RiskCard'
import EntityTable from './EntityTable'

export default function LiveDemo(){
  const [text,setText] = React.useState("Hi I'm Alex Lee, email alex.lee@example.com, SSN 123-45-6789, card 4242 4242 4242 4242.")
  const [action,setAction] = React.useState<'REDACT'|'MASK'|'SYNTHESIZE'|'NONE'>('REDACT')
  const [dp,setDp] = React.useState(false)

  const [res,setRes] = React.useState<any>(null)
  const [loading,setLoading] = React.useState(false)

  const submit = async() => {
    setLoading(true)
    try{
      const r = await api.post('/process', { text, action, options:{dp} })
      setRes(r.data)
    } finally { setLoading(false) }
  }

  return (
    <div className="grid grid-cols-1 lg:grid-cols-2 gap-4">
      <div className="card">
        <div className="font-semibold mb-2">Input</div>
        <textarea className="input h-40" value={text} onChange={e=>setText(e.target.value)} />
        <div className="mt-3 flex flex-wrap gap-2">
          <select className="input w-auto" value={action} onChange={e=>setAction(e.target.value as any)}>
            <option>REDACT</option><option>MASK</option><option>SYNTHESIZE</option><option>NONE</option>
          </select>
          <label className="flex items-center gap-2 text-sm">
            <input type="checkbox" checked={dp} onChange={e=>setDp(e.target.checked)} />
            Differential Privacy counts
          </label>
          <button className="btn" onClick={submit} disabled={loading}>{loading?'Processing...':'Run'}</button>
        </div>
      </div>

      <div className="grid gap-4">
        <RiskCard risk={res?.risk}/>
        <div className="card">
          <div className="font-semibold mb-2">Output</div>
          <div className="text-sm whitespace-pre-wrap">{res?.transformedText ?? '—'}</div>
          {res?.audit?.dpCounts && (
            <div className="text-xs opacity-70 mt-3">DP counts: {JSON.stringify(res.audit.dpCounts)}</div>
          )}
        </div>
      </div>

      <div className="lg:col-span-2">
        <EntityTable entities={res?.entities ?? []}/>
      </div>
    </div>
  )
}
