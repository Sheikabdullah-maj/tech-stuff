package shebilsolutions.quizapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluateRequestDTO {
    private List<SubmittedAnswerDTO> data;
}
