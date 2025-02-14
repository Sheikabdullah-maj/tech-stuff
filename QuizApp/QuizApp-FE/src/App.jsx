import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import LandingPage from './components/LandingPage'
import QuizSection from './components/QuizSection'

function App() {

  const [showQuiz, setShowQuiz] = useState(false);

  
  function showQuizPage(flag) {
    setShowQuiz(flag);
  }
  
  return (
    <>
    {showQuiz ? 
      <QuizSection showQuizPage={showQuizPage}/> : <LandingPage showQuizPage={showQuizPage}/>
    }
    </>
  )
}

export default App
