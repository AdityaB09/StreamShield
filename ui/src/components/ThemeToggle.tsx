import React from 'react'

export default function ThemeToggle(){
  const [dark,setDark] = React.useState(true)
  React.useEffect(()=> {
    document.documentElement.classList.toggle('dark', dark)
  },[dark])
  return (
    <button className="btn" onClick={()=>setDark(d=>!d)}>
      {dark ? '☀️ Light' : '🌙 Dark'}
    </button>
  )
}
