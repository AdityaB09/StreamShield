import React from 'react'
import ThemeToggle from './ThemeToggle'

export default function Header(){
  return (
    <header className="flex items-center justify-between mb-6">
      <div className="flex items-center gap-3">
        <div className="h-10 w-10 rounded-2xl bg-sky-500/20 flex items-center justify-center">
          <span className="text-sky-600 font-black">S</span>
        </div>
        <div>
          <div className="font-extrabold text-xl">StreamShield</div>
          <div className="text-sm opacity-70">Privacy-Preserving Text Transformer</div>
        </div>
      </div>
      <ThemeToggle/>
    </header>
  )
}
