package libmas.admin_only.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CUDResponseDTO {
    private boolean processed;
    private Object data;
    private String errorCode;
    private String errorDescription;
}
