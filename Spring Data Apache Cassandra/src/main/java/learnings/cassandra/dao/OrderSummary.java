package learnings.cassandra.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Map;

@Table("order_summary")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderSummary {
    @PrimaryKey("order_id")
    private Integer orderId;

    @Column("customer_id")
    private String customerId;

    @Column("invoice_amount")
    private Double invoiceAmount;

    @Column("invoice_date")
    private LocalDateTime invoiceDate;

    @Column("paid")
    private Boolean paid;

    @Column("paymode")
    private String payMode;

    @Column("purchase_items")
    private Map<String, Map<String, Double>> purchaseItems;

    @Column("return_items")
    private Map<String, Map<String, Double>> returnItems;
}
