
import React, { useEffect, useState } from 'react'

export default function QuizTimer({ timeout, timeoutCallback}) {
  const [remainingTime, setRemainingTime] = useState(timeout);

  useEffect(() => {
   const timeoutObj = setTimeout(timeoutCallback, timeout); 
    return () => {
      clearTimeout(timeoutObj);
    }

  },[timeout, timeoutCallback]);

  useEffect(() => {
    const setIntervalId = setInterval(() =>{
      setRemainingTime((currTime) => currTime -100);
    }, 100);
    return () => {
      clearInterval(setIntervalId);
    }
  },[]);
  
    return (
    <progress id="progress" value={remainingTime} max={timeout}></progress>
  )
}
