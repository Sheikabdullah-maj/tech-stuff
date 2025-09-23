import { useState } from 'react'
import QUESTIONS from './datasource/question.jsx'
import quizComplete from '../assets/quiz-complete.png'; 
import QuizTimer from './QuizTimer.jsx';

export default function Quiz() {

    const [selectedAnswers, setSelectedAnswers] = useState([]);
    let currentQuestionIndex= selectedAnswers.length;

    const computeScore = () => {
       let correctAnswers = 0;  
       for (let i=0; i< QUESTIONS.length; i++) {
            let selectedAnswer = selectedAnswers[i];
            let actualAnswer = QUESTIONS[i].value;
            if (selectedAnswer == actualAnswer) {
                correctAnswers++;
            }
       }  
       return correctAnswers;   
    }

 
    if(currentQuestionIndex == QUESTIONS.length){
        let score = computeScore();
        let scoreInString = score + '/' + QUESTIONS.length
        return <div id="summary">
            <img src={quizComplete}/>
            <h2>Quiz completed!!</h2>
            <h2>Here is your score {scoreInString}</h2>
        </div>
    }
    let currentQuestion = QUESTIONS[currentQuestionIndex];

    function handleOptionSelect(selectedAnswer) {
        setSelectedAnswers(prevAnswers => {
            return [...prevAnswers, selectedAnswer]
        })
    }

    const shuffle = (array) => { 
        return array.sort(() => Math.random() - 0.5); 
    }; 
    
    // Usage 
    const shuffledArray = currentQuestion.answers;
    shuffle(shuffledArray); 

    function timeoutHandler() {
        console.log('time out callback invoked');
        handleOptionSelect(null);
    }

    return (
        <div id='quiz'>
        <QuizTimer timeout={30000} timeoutCallback={timeoutHandler} key={currentQuestionIndex} />
        <div className='question'>
        <h2>{currentQuestion.text}</h2>
        <ul className='answers'>
        {shuffledArray.map((option) => <li className='answer' key={option}>
            <button onClick={() => handleOptionSelect(option)}>{option}</button>
        </li>
        )}
        </ul>
        </div></div>);
   
}