package techstuff.watchdog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackerResponseDTO {
    private Long id;
    private String name;
    private String deskNumber;
    private LocalDate date;
    private LocalDateTime tapIn;
    private LocalDateTime tapOut;
}
