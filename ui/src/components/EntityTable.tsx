type Hit = {type:string,text:string,start:number,end:number,confidence:number,source:string}

export default function EntityTable({entities}:{entities:Hit[]}) {
  return (
    <div className="card overflow-auto">
      <div className="font-semibold mb-3">Detected Entities</div>
      <table className="w-full text-sm">
        <thead className="text-left opacity-70">
          <tr><th>Type</th><th>Text</th><th>Conf.</th><th>Source</th><th>Span</th></tr>
        </thead>
        <tbody>
          {entities?.map((e,i)=>(
            <tr key={i} className="border-t border-slate-200/60 dark:border-slate-800">
              <td className="py-2">{e.type}</td>
              <td className="py-2">{e.text}</td>
              <td className="py-2">{e.confidence.toFixed(2)}</td>
              <td className="py-2">{e.source}</td>
              <td className="py-2">{e.start}-{e.end}</td>
            </tr>
          )) || null}
        </tbody>
      </table>
    </div>
  )
}
