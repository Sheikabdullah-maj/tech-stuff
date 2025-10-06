package techstuff.watchdog.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "employee_tracker")
public class EmployeeTapTracker {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    @Column(name = "desk_number")
    private String deskNumber;
    private LocalDate date;

    @Column(name = "tap_in")
    private LocalDateTime tapIn;

    @Column(name = "tap_out")
    private LocalDateTime tapOut;
}


