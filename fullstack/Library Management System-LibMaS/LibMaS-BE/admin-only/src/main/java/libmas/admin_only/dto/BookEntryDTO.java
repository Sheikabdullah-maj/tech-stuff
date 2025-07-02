package libmas.admin_only.dto;


import lombok.Data;

@Data
public class BookEntryDTO {
    private Long Id;
    private Long categoryId;
    private String title;
    private String author;
    private Integer totalCopies;
    private String publications;
}
