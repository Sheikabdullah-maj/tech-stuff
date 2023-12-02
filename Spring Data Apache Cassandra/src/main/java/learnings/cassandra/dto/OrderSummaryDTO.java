package learnings.cassandra.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderSummaryDTO {
    private Integer orderId;
    private String customerId;
    private Double invoiceAmount;
    private LocalDate invoiceDate;
    private Boolean paid;
    private String payMode;
    private Map<String, Map<String, Double>> purchaseItems;
    private Map<String, Map<String, Double>> returnItems;
}
