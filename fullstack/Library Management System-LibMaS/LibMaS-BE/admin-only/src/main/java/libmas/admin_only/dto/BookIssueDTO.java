package libmas.admin_only.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookIssueDTO {
    private Long bookId;
    private Long memberId;
    private LocalDate dateOfIssue;
    private LocalDate expectedDateOfReturn;

    public void computeDateOfReturn(){
        expectedDateOfReturn = dateOfIssue.plusDays(20);
    }
}
