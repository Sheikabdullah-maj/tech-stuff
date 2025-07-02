package libmas.admin_only.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "book_issue_tracker")
@Data
public class BookIssueTracker {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private Long memberId;
    private LocalDate dateOfIssue;
    private LocalDate expectedDateOfReturn;
    private LocalDate actualDateOfReturn;
}
