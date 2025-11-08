import React from 'react'
import { api } from '../lib/api'

export default function AuditTable() {
  const [rows, setRows] = React.useState<any[]>([])
  const [error, setError] = React.useState<string | null>(null)

  React.useEffect(() => {
    api
      .get('/audit')
      .then((r) => {
        const data = r.data
        // 🔒 Make sure we always store an array to avoid `.map` crashing
        if (Array.isArray(data)) {
          setRows(data)
        } else {
          console.warn('Unexpected /api/audit payload:', data)
          setRows([])
        }
      })
      .catch((err) => {
        console.error('Failed to load audit data', err)
        setError('Failed to load audit trail')
        setRows([])
      })
  }, [])

  return (
    <div className="card overflow-auto">
      <div className="font-semibold mb-3">Audit Trail</div>

      {error && (
        <div className="text-xs text-red-400 mb-2">
          {error}
        </div>
      )}

      <table className="w-full text-sm">
        <thead className="text-left opacity-70">
          <tr>
            <th>ID</th>
            <th>Score</th>
            <th>Created</th>
            <th>Snippet</th>
          </tr>
        </thead>
        <tbody>
          {Array.isArray(rows) && rows.length > 0 ? (
            rows.map((r: any) => (
              <tr
                key={r.id}
                className="border-t border-slate-200/60 dark:border-slate-800"
              >
                <td className="py-2">{r.id}</td>
                <td className="py-2">
                  {((r.documentScore ?? 0) * 100).toFixed(0)}
                </td>
                <td className="py-2">{r.createdAt}</td>
                <td className="py-2">
                  {((r.transformedText ?? '') as string).slice(0, 120)}...
                </td>
              </tr>
            ))
          ) : (
            <tr className="border-t border-slate-200/60 dark:border-slate-800">
              <td className="py-3 text-sm opacity-60" colSpan={4}>
                No audit records yet – run the demo above to generate some.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  )
}
