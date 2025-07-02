package libmas.admin_only.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailByIdResponseDTO {
    private Long id;
    private Long categoryId;
    private String title;
    private String author;
    private Integer totalCopies;
    private String publications;
}

