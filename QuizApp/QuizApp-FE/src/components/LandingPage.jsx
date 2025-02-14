
function LandingPage({showQuizPage}) {

    function startQuiz(){
        showQuizPage(true);
    }

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
          <div className="w-full max-w-md p-8 space-y-6 bg-white shadow-lg rounded-2xl">
            <h2 className="text-2xl font-bold text-center text-gray-800">Quiz App</h2>
    
            {/* <form className="space-y-4"> */}
              {/* Email Field */}
              <div>
                <label className="block text-sm font-medium text-gray-700">Email</label>
                <input
                  type="email"
                  className="w-full p-3 mt-1 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500"
                  placeholder="Enter your email"
                />
              </div>
              <button
                onClick={startQuiz}
                className="w-full p-3 font-medium text-white bg-blue-500 rounded-lg hover:bg-blue-600"
              >
                Start
              </button>
            {/* </form> */}
            </div>
        </div>
      );
}

export default LandingPage;