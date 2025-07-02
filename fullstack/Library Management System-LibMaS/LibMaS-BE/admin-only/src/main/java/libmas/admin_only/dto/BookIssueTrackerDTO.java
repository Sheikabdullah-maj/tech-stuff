package libmas.admin_only.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;


@Data
public class BookIssueTrackerDTO {
    private Long id;
    private Long bookId;
    private Long memberId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfIssue;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedDateOfReturn;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate actualDateOfReturn;
}
