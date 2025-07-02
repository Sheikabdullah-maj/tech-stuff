package libmas.admin_only.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BooksLendedByMemberDetailDTO {
    private Long bookId;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedDateOfReturn;
    private String fineCheckNeeded;

    /*@JsonFormat(pattern = "yyyy-MM-dd")
    public void setExpectedDateOfReturn(LocalDate expectedDateOfReturn) {
        this.expectedDateOfReturn = expectedDateOfReturn;
    }

    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate getExpectedDateOfReturn() {
        return expectedDateOfReturn;
    }*/
}
