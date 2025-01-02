package libmas.admin_only.repository;

import libmas.admin_only.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>, CrudRepository<Category, Long> {
}
