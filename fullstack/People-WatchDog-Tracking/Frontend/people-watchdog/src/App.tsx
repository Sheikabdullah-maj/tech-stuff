import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Layout from './layout/Layout'
import WatchDogGrid from './components/WatchDogGrid'

function App() {

  return (
    <>
      <Layout/>
      <WatchDogGrid/>
    </>
  )
}

export default App
