package libmas.admin_only.controller;

import libmas.admin_only.dto.*;
import libmas.admin_only.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
//@CrossOrigin(origins = { "http://localhost:5173" })
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/listBooks")
    public Page<BookDetailsResponseDTO> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestBody BookDetailsRequestDTO bookDetailsRequestDTO
                                                    ) {
        return bookService.getAllBooksWithPagination(bookDetailsRequestDTO, page, size);
    }

    @PostMapping("/saveBook")
    public ResponseEntity<Object> createBook(@RequestBody BookEntryDTO bookEntryDTO) {
        return  ResponseEntity.ok(CUDResponseDTO.builder().processed(bookService.saveBook(bookEntryDTO))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return  ResponseEntity.ok(CUDResponseDTO.builder().processed(true)
                .build());
    }

    @GetMapping("/getAllCategories")
    public List<CategoryDTO> getAllCategories(){
        return bookService.getCategories();
    }

    @PostMapping("/saveCategory")
    public CategoryDTO saveCategory(@RequestBody CategoryDTO categoryDTO){
        return bookService.saveCategory(categoryDTO);
    }

    @GetMapping("/getBookDetails/{id}")
    public BookDetailByIdResponseDTO getBookDetailsById(@PathVariable Long id) {
        return bookService.getBookDetailsById(id);
    }
}
