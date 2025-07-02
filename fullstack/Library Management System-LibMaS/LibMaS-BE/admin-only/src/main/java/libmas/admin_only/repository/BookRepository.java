package libmas.admin_only.repository;

import jakarta.persistence.Tuple;
import libmas.admin_only.entity.BookDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface BookRepository extends PagingAndSortingRepository<BookDetails, Long>, CrudRepository<BookDetails, Long> {

    @Query(value = " select bd.id, " +
            " bd.title,bd.author,c.description as category,bd.publications,bd.total_copies as totalCopies " +
            " from book_details bd left outer join category c on c.id = bd.category_id " +
            " where " +
            " (length(coalesce(:id,''))=0  OR bd.id = :id) " +
            " and (length(coalesce(:title,''))=0  OR bd.title LIKE %:title%) " +
            " and (length(coalesce(:author,''))=0  OR bd.author LIKE %:author%) " +
            " and (length(coalesce(:category,''))=0  OR c.id = :category ) " +
            " order by bd.title ",nativeQuery = true,
            countQuery = "select count(*) from book_details bd " +
                    " where " +
                    " (length(coalesce(:id,''))=0  OR bd.id = :id) " +
                    " and (length(coalesce(:title,''))=0  OR bd.title LIKE %:title%) " +
                    " and (length(coalesce(:author,''))=0  OR bd.author LIKE %:author%) " +
                    " and (length(coalesce(:category,''))=0  OR c.id = :category ) "
    )
    Page<Map<Object, Object>> getBasicBookDetailsByPagination(@Param("id") Long id,@Param("title") String title,
                                                              @Param("author") String author,
                                                              @Param("category") String category,
                                                              Pageable pageable);


    @Query(value = "select total_copies from book_details where id= :bookId", nativeQuery = true)
    Integer getTotalCopiesById(@Param("bookId") Long id);


    @Modifying
    @Query(value = "update book_details set total_copies = (total_copies-1) where id=:bookId", nativeQuery = true)
    void updateTotalCopiesById(@Param("bookId") Long bookId);

}
