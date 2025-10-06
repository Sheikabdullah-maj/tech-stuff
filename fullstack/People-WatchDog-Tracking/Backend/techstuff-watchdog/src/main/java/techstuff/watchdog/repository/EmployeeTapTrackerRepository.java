package techstuff.watchdog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techstuff.watchdog.model.EmployeeTapTracker;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeTapTrackerRepository extends JpaRepository<EmployeeTapTracker, Long> {

    List<EmployeeTapTracker> findAllByDateOrderByNameAsc(LocalDate date);
}
