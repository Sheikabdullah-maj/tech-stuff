package libmas.admin_only.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import libmas.admin_only.dto.*;
import libmas.admin_only.entity.BookDetails;
import libmas.admin_only.entity.Category;
import libmas.admin_only.repository.BookRepository;
import libmas.admin_only.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    public Page<BookDetailsResponseDTO> getAllBooksWithPagination(BookDetailsRequestDTO bookDetailsRequestDTO,int pageNumber, int pageSize){
        Page<Map<Object, Object>> result = bookRepository.getBasicBookDetailsByPagination(bookDetailsRequestDTO.getId(),
                bookDetailsRequestDTO.getTitle(), bookDetailsRequestDTO.getAuthor(),
                bookDetailsRequestDTO.getCategory(),
                PageRequest.of(pageNumber, pageSize));
        List<Map<Object, Object>> rows = result.getContent();
        List<BookDetailsResponseDTO> bookDetailsDTOS = new ArrayList<>();
        for (Map<Object, Object> row:
             rows) {
            bookDetailsDTOS.add(objectMapper.convertValue(row, BookDetailsResponseDTO.class));
        }
        return new PageImpl<BookDetailsResponseDTO>(bookDetailsDTOS,PageRequest.of(pageNumber, pageSize), result.getTotalElements());
    }

    public boolean saveBook(BookEntryDTO bookEntryDTO){
        BookDetails bookDetails = new BookDetails();
        BeanUtils.copyProperties(bookEntryDTO, bookDetails);
        bookDetails = bookRepository.save(bookDetails);
        log.info("bookDetails saved for book id:{}",bookDetails.getId());
        return bookDetails.getId()!=null;
    }

    public CategoryDTO saveCategory(CategoryDTO categoryDTO){
        Category category = objectMapper.convertValue(categoryDTO, Category.class);
        category = categoryRepository.save(category);
        if(Objects.nonNull(category) && category.getId() >0){
            return objectMapper.convertValue(category, CategoryDTO.class);
        }
        else {
            throw new RuntimeException("Error when update Category");
        }
    }

    public List<CategoryDTO> getCategories(){
        Iterator<Category> categoriesItr = categoryRepository.findAll().iterator();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        while(categoriesItr.hasNext()){
            Category category = categoriesItr.next();
            categoryDTOS.add(CategoryDTO.builder().id(category.getId()).description(category.getDescription()).build());
        }
        return categoryDTOS;
    }

    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }

    public BookDetailByIdResponseDTO getBookDetailsById(Long id) {
        Optional<BookDetails> bookDetailRawObject = bookRepository.findById(id);
        if(bookDetailRawObject.isPresent()){
            return objectMapper.convertValue(bookDetailRawObject.get(), BookDetailByIdResponseDTO.class);
        }
        return BookDetailByIdResponseDTO.builder().build();
    }




}




