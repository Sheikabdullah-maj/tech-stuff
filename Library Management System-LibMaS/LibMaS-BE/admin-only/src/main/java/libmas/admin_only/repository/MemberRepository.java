package libmas.admin_only.repository;

import libmas.admin_only.entity.MemberDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface MemberRepository extends PagingAndSortingRepository<MemberDetails, Long>, CrudRepository<MemberDetails, Long> {

    @Query(
            value = " select id,name,mobile_number as mobileNumber, date_of_join as dateOfJoin, status from member_details md" +
                    " where (length(coalesce(:id,''))=0 OR id= :id)" +
                    " and (length(coalesce(:name,''))=0 OR name like %:name%)" +
                    " and (length(coalesce(:mobileNumber,''))=0 OR mobile_number like %:mobileNumber%) ",
            countQuery = " select count(*) from member_details md" +
                    " where (length(coalesce(:id,''))=0 OR id= :id)" +
                    " and (length(coalesce(:name,''))=0 OR name like %:name%)" +
                    " and (length(coalesce(:mobileNumber,''))=0 OR mobile_number like %:mobileNumber%)",
            nativeQuery = true
    )
    Page<Map<Object, Object>> getMemberDetailsByPagination(@Param("id") Long id, @Param("name") String name,
                                                              @Param("mobileNumber") String mobileNumber,
                                                              Pageable pageable);
}
