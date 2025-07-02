package shebilsolutions.quizapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestionDTO {
    private Integer questionId;
    private String question;
    private Map<String, String> choices;
}
