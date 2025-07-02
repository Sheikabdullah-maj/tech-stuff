package libmas.admin_only.repository;

import libmas.admin_only.dto.BookIssueDTO;
import libmas.admin_only.entity.BookIssueTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookIssueTrackerRepository extends CrudRepository<BookIssueTracker, Long>, PagingAndSortingRepository<BookIssueTracker, Long> {

    BookIssueTracker findByBookIdAndMemberId(Long bookId, Long memberId);

    @Query(value = "select tracker.book_id as bookId,bd.title as title,tracker.expected_date_of_return as expectedDateOfReturn, " +
            " case when (tracker.expected_date_of_return < current_date()) then 'Y' else 'N' end as fineCheckNeeded  " +
            " from book_issue_tracker tracker, book_details bd where tracker.member_id= :memberId and tracker.book_id = bd.id and tracker.actual_date_of_return is null ",nativeQuery = true)
    List<Map<String, Object>> findBooksLendedForMember(@Param("memberId") String memberId);

    Page<BookIssueTracker> findAll(Pageable pageable);
}
