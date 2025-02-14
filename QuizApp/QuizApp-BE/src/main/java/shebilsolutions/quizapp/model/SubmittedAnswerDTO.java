package shebilsolutions.quizapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedAnswerDTO {
    private String questionId;
    private List<String> selectedOptions;
}
