package libmas.admin_only.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import libmas.admin_only.dto.*;
import libmas.admin_only.entity.BookIssueTracker;
import libmas.admin_only.repository.BookIssueTrackerRepository;
import libmas.admin_only.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookLendingService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookIssueTrackerRepository issueTrackerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public CUDResponseDTO issueBook(BookIssueDTO issueDTO) {
        Integer totalCopies = bookRepository.getTotalCopiesById(issueDTO.getBookId());
        if (Objects.nonNull(totalCopies) && totalCopies < 1) {
            throw new RuntimeException("Copies not available, Please check the book Id");
        }
        issueDTO.computeDateOfReturn();
        log.info("{} copies available for the book id :{}", totalCopies, issueDTO.getBookId());
        BookIssueTracker issueTracker = objectMapper.convertValue(issueDTO, BookIssueTracker.class);
        issueTrackerRepository.save(issueTracker);
        log.info("book issue entry added");
        bookRepository.updateTotalCopiesById(issueDTO.getBookId());
        log.info("book issued member Id:{}, bookId : {}", issueDTO.getMemberId(), issueDTO.getBookId());
        return CUDResponseDTO.builder().processed(true).build();
    }

    public CUDResponseDTO returnBook(BookReturnDTO requestDTO) {
        BookIssueTracker trackerRecord = issueTrackerRepository.findByBookIdAndMemberId(requestDTO.getBookId(), requestDTO.getMemberId());
        CUDResponseDTO responseDTO = CUDResponseDTO.builder().processed(false).build();
        if (Objects.nonNull(trackerRecord)) {
            trackerRecord.setActualDateOfReturn(requestDTO.getActualDateOfReturn());
            issueTrackerRepository.save(trackerRecord);
            log.info("Book Returned!!!");
            responseDTO.setProcessed(true);
        }
        return responseDTO;
    }

    public CUDResponseDTO getExpectedDateOfReturn(Long memberId,Long bookId) {
        log.info("Request to get expected date of return for book Id:{} belongs to member id:{}",bookId, memberId);
        BookIssueTracker trackerRecord = issueTrackerRepository.findByBookIdAndMemberId(bookId, memberId);
        CUDResponseDTO responseDTO = CUDResponseDTO.builder().processed(false).build();
        if (Objects.nonNull(trackerRecord)) {
            responseDTO.setProcessed(true);
            responseDTO.setData(trackerRecord.getExpectedDateOfReturn());
        }
        else {
            responseDTO.setProcessed(true);
            responseDTO.setErrorCode("NOT_FOUND");
            responseDTO.setErrorDescription("RECORD_NOT_FOUND");
        }
        return responseDTO;
    }

    public Map<Long, Object> findBooksLendedForMember(String memberId){
        List<Map<String, Object>> data = issueTrackerRepository.findBooksLendedForMember(memberId);
        Map<Long, Object> lendingData = new HashMap<>();
        if(Objects.nonNull(data) && !data.isEmpty()){
            for(Map<String, Object> record : data) {
                BooksLendedByMemberDetailDTO detailDTO =  objectMapper.convertValue(record, BooksLendedByMemberDetailDTO.class);
                detailDTO.setExpectedDateOfReturn( ((java.sql.Date) record.get("expectedDateOfReturn")).toLocalDate());
                lendingData.put(detailDTO.getBookId(), detailDTO);
            }
        }
        return lendingData;
    }

    public Page<BookIssueTrackerDTO> findBookIssueTrackerRecord(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("actualDateOfReturn","expectedDateOfReturn"));
        log.info("Book Issue Tracker Request received");
        Page<BookIssueTracker> records= issueTrackerRepository.findAll(pageable);

        List<BookIssueTrackerDTO> responseDTOs = records.getContent().stream().map(rec ->
            objectMapper.convertValue(rec, BookIssueTrackerDTO.class)).collect(Collectors.toList());

        return new PageImpl<BookIssueTrackerDTO>(responseDTOs,PageRequest.of(pageNumber, pageSize), records.getTotalElements());
    }
}
