import React from 'react'
import { api } from '../lib/api'

export default function AuditTable(){
  const [rows,setRows] = React.useState<any[]>([])
  React.useEffect(()=>{
    api.get('/audit').then(r=>setRows(r.data))
  },[])
  return (
    <div className="card overflow-auto">
      <div className="font-semibold mb-3">Audit Trail</div>
      <table className="w-full text-sm">
        <thead className="text-left opacity-70">
          <tr><th>ID</th><th>Score</th><th>Created</th><th>Snippet</th></tr>
        </thead>
        <tbody>
          {rows.map((r:any)=>(
            <tr key={r.id} className="border-t border-slate-200/60 dark:border-slate-800">
              <td className="py-2">{r.id}</td>
              <td className="py-2">{(r.documentScore*100).toFixed(0)}</td>
              <td className="py-2">{r.createdAt}</td>
              <td className="py-2">{(r.transformedText||'').slice(0,120)}...</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
