package shebilsolutions.quizapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluateResponseDTO {
    private Integer submitted =0;
    private Integer correct =0;
    private Integer incorrect =0;
    private String score;

    public void initResponseValues() {
        this.correct =0;
        this.incorrect =0;
        this.submitted =0;
    }

    public void incrementCorrect(){
        this.correct +=1;
    }
    public void incrementIncorrect(){
        this.incorrect +=1;
    }

}
