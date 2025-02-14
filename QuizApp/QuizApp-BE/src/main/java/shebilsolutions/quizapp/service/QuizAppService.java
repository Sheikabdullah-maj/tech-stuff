package shebilsolutions.quizapp.service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shebilsolutions.quizapp.model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class QuizAppService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuizAnswerCollection quizAnswerCollection;

    public QuizQuestionResponseDTO getQuestionCollection() {
        QuizQuestionResponseDTO responseDTO =  null;
        try {
            responseDTO = objectMapper.readValue(ClassLoader.getSystemResource("quizdatasource.json"),
                    QuizQuestionResponseDTO.class);
            return responseDTO;
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseDTO;
    }

    public EvaluateResponseDTO evaluateSubmission(EvaluateRequestDTO requestDTO){
        List<SubmittedAnswerDTO> submittedAnswerDTOList = requestDTO.getData();
        Map<String,List<String>> answerList = quizAnswerCollection.getData();
        EvaluateResponseDTO evaluateResponseDTO = EvaluateResponseDTO.builder().build();
        evaluateResponseDTO.initResponseValues();
        evaluateResponseDTO.setSubmitted(submittedAnswerDTOList.size());
        for(SubmittedAnswerDTO submitted : submittedAnswerDTOList){
            String questionId = submitted.getQuestionId();
            List<String> answers = answerList.get(questionId);
            log.info("submitted options : "+submitted.getSelectedOptions());
            log.info("answers: "+answers);
            if(submitted.getSelectedOptions().equals(answers)){
                evaluateResponseDTO.incrementCorrect();
            }
            else {
                evaluateResponseDTO.incrementIncorrect();
            }
        }
        evaluateResponseDTO.setScore(((evaluateResponseDTO.getCorrect().doubleValue()/evaluateResponseDTO.getSubmitted().doubleValue()) * 100) + "%");
        return evaluateResponseDTO;
    }
}
