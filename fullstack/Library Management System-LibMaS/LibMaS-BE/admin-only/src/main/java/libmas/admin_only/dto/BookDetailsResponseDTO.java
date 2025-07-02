package libmas.admin_only.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookDetailsResponseDTO {
    private Long id;
    private String title;
    private String author;
    private String category;
    private String publications;
    private Integer totalCopies;
}
