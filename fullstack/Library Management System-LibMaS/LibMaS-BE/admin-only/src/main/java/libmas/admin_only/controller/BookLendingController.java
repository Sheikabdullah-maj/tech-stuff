package libmas.admin_only.controller;

import libmas.admin_only.dto.BookIssueDTO;
import libmas.admin_only.dto.BookIssueTrackerDTO;
import libmas.admin_only.dto.BookReturnDTO;
import libmas.admin_only.dto.CUDResponseDTO;
import libmas.admin_only.entity.BookIssueTracker;
import libmas.admin_only.service.BookLendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/lend")
@Slf4j
public class BookLendingController {

    @Autowired
    private BookLendingService bookLendingService;

    @PostMapping("/issue")
    public CUDResponseDTO issueBook(@RequestBody BookIssueDTO bookIssueDTO){
        log.info("Book Issue Request received for Book Id :{}. Member Id : {}",bookIssueDTO.getBookId(), bookIssueDTO.getMemberId());
        return bookLendingService.issueBook(bookIssueDTO);
    }


    @GetMapping("/getExpectedDateOfReturn")
    public CUDResponseDTO getExpectedDateOfReturn(@RequestHeader("memberId") Long memberId,
                                                  @RequestHeader("bookId") Long bookId){
        return bookLendingService.getExpectedDateOfReturn(memberId,bookId);
    }

    @PostMapping("/return")
    public CUDResponseDTO issueBook(@RequestBody BookReturnDTO bookReturnDTO){
        log.info("Book Return Request received for Book Id :{}. Member Id : {}",bookReturnDTO.getBookId(), bookReturnDTO.getMemberId());
        return bookLendingService.returnBook(bookReturnDTO);
    }

    @GetMapping("/getBooksLendedForMember/{memberId}")
    public Map<Long, Object> getBooksLendedForMember(@PathVariable("memberId") String memberId){
        log.info("getBooksLendedForMember request raised for member : {}",memberId);
        return bookLendingService.findBooksLendedForMember(memberId);
    }

    @GetMapping("/getBookTrackerRecord")
    public Page<BookIssueTrackerDTO> getBookTrackerRecord(@RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize
                                     ) {
        return bookLendingService.findBookIssueTrackerRecord(pageNumber, pageSize);

    }

}
