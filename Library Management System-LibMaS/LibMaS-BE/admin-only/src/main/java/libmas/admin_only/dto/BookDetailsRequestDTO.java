package libmas.admin_only.dto;

import lombok.*;

@Data
public class BookDetailsRequestDTO {
    private Long Id;
    private String title;
    private String author;
    private String category;
}
