package libmas.admin_only.repository;

import libmas.admin_only.entity.UserDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends CrudRepository<UserDetail, Long > {
    UserDetail findByMail(String mail);
}
