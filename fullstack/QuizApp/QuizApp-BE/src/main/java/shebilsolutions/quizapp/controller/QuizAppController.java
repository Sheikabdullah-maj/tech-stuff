package shebilsolutions.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shebilsolutions.quizapp.model.EvaluateRequestDTO;
import shebilsolutions.quizapp.model.EvaluateResponseDTO;
import shebilsolutions.quizapp.model.QuizQuestionResponseDTO;
import shebilsolutions.quizapp.service.QuizAppService;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("http://localhost:5173/")
public class QuizAppController {

    @Autowired
    private QuizAppService quizAppService;

    @GetMapping("/getQuestions")
    public QuizQuestionResponseDTO getQuestions(){
        return quizAppService.getQuestionCollection();
    }

    @PostMapping("/evaluateSubmission")
    public EvaluateResponseDTO evaluateSubmission(@RequestBody EvaluateRequestDTO requestDTO) {
        return quizAppService.evaluateSubmission(requestDTO);
    }
}
