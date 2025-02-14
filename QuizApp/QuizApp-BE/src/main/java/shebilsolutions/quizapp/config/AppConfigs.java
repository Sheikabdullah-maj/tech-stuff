package shebilsolutions.quizapp.config;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shebilsolutions.quizapp.model.QuizAnswerCollection;
import shebilsolutions.quizapp.model.QuizQuestionResponseDTO;

import java.io.IOException;

@Configuration
@Slf4j
public class AppConfigs {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public QuizAnswerCollection getQuizAnswerCollection(ObjectMapper objectMapper) throws IOException {
        QuizAnswerCollection answerCollection =  null;
        try {
            answerCollection = objectMapper.readValue(ClassLoader.getSystemResource("quiz_answer_datasource.json"),
                    QuizAnswerCollection.class);
            return answerCollection;
        } catch (Exception e) {
            log.error("Exception while read answer collection",e);
            throw e;
        }
    }
}
