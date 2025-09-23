import { useState,useEffect } from "react";
import { quizAppApi } from "./apiconsumer/QuizAppAPIConsumer";



const QuizSection = ({showQuizPage}) => {
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [selectedOptions, setSelectedOptions] = useState([]);
  const [submittedAnswers, setSubmittedAnswers] = useState({
    data: []
  });
  const [quizFinished, setQuizFinished] = useState(false);
  const [quizData, setQuizData] = useState([]);
  const [evaluatedResult, setEvaluatedResult] = useState({});

  const questionData = quizData[currentQuestionIndex];
  const handleOptionClick = (key) => {
    
      setSelectedOptions((prev) =>
        prev.includes(key)
          ? prev.filter((item) => item !== key) // Deselect option
          : [...prev, key] // Select option
      );
    
  };

  const updateSelectedOptions = () => {
    
    if(submittedAnswers.data.length >= (currentQuestionIndex+1)){
        setSelectedOptions((prev) => submittedAnswers.data[currentQuestionIndex].selectedOptions);
    }
    else{
        setSelectedOptions([])
    }
  }

  useEffect(() => {
    updateSelectedOptions();
  },[currentQuestionIndex])

  useEffect(() => {
    getQuizData();
  },[]);

  useEffect(() => {
    if(quizFinished && submittedAnswers.data.length) {
        evaluateSubmission();
    }
  },[quizFinished, submittedAnswers]);


  async function getQuizData(){
    const restResponse = await quizAppApi.getQuestions();
    const responseData = await restResponse.data.data;
    setQuizData((prevData) => responseData);
  }

  function collectSubmittedAnswers(){
    const submittedQuestion = {
        questionId :questionData.questionId,
        selectedOptions : selectedOptions
    }
    let existing = false;
    if(submittedAnswers.data.find(q => (q.questionId === questionData.questionId)) !== undefined){
        existing = true;
    }
    setSubmittedAnswers((prevState) =>
        ({
            data : (existing) ? (prevState.data.map((q) =>
                q.questionId === questionData.questionId ? { ...q, selectedOptions: selectedOptions } : q
              )) : [...prevState.data, submittedQuestion]
        }));
    

  }

  async function evaluateSubmission() {
    if (submittedAnswers.data.length > 0) {
        const restResponse = await quizAppApi.evaluateSubmission(submittedAnswers);
        const evaluatedResult = restResponse.data;        
        setEvaluatedResult(prev=>evaluatedResult);
    }
  };

  const handleNext = () => {
    if (currentQuestionIndex < quizData.length - 1) {
      collectSubmittedAnswers();
      setCurrentQuestionIndex(currentQuestionIndex + 1);
    }
  };

  const handlePrevious = () => {
    if (currentQuestionIndex > 0) {
      setCurrentQuestionIndex(currentQuestionIndex - 1);
    }
  };

  function handleFinishQuiz() {
    collectSubmittedAnswers();
    setQuizFinished(true);
  }

  function restartQuiz(){
    showQuizPage(false);
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <div className="w-full max-w-xl p-6 space-y-4 bg-white shadow-lg rounded-2xl">
        {quizFinished ? (
          <div className="text-center">
            <h2 className="text-2xl font-semibold text-gray-800">Quiz Completed!</h2>
            <p className="text-lg font-medium text-gray-600">
              Your Score: {evaluatedResult.score}
            </p>
            <button
              onClick={restartQuiz}
              className="px-4 py-2 mt-4 text-white bg-blue-500 rounded-lg hover:bg-blue-600"
            >
              Restart Quiz
            </button>
          </div>
        ) : questionData !=undefined ? (
          <>
            <h2 className="text-xl font-semibold text-center text-gray-800">
              {questionData.question}
            </h2>

            <div className="grid gap-3">
              {Object.entries(questionData.choices).map(([key, value]) => (
                <button
                  key={key}
                  className={`w-full p-3 text-lg font-medium border rounded-lg transition ${
                    selectedOptions.includes(key)
                      ? "bg-blue-500 text-white"
                      : "bg-white hover:bg-gray-200"
                  }`}
                  onClick={() => handleOptionClick(key)}
                >
                  {value}
                </button>
              ))}
            </div>

            

            {/* Navigation Buttons */}
            <div className="flex justify-between mt-4">
              <button
                onClick={handlePrevious}
                className="px-4 py-2 text-white bg-gray-500 rounded-lg disabled:opacity-50"
                disabled={currentQuestionIndex === 0}
              >
                Previous
              </button>

              {currentQuestionIndex < quizData.length - 1 ? (
                <button
                  onClick={handleNext}
                  className="px-4 py-2 text-white bg-blue-500 rounded-lg disabled:opacity-50"
                  disabled={selectedOptions.length === 0}
                >
                  Next
                </button>
              ) : (
                <button
                  onClick={handleFinishQuiz}
                  className="px-4 py-2 text-white bg-green-500 rounded-lg disabled:opacity-50"
                  disabled={selectedOptions.length === 0}
                >
                  Finish Quiz
                </button>
              )}
            </div>
          </>
        ) : (<>Loading</>)}
      </div>
    </div>
  );
};

export default QuizSection;
