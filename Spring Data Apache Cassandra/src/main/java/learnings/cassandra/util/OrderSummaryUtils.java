package learnings.cassandra.util;

import learnings.cassandra.dao.OrderSummary;
import learnings.cassandra.dto.OrderSummaryDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderSummaryUtils {

    public static OrderSummaryDTO convertOrderSummaryDAOToDTO(OrderSummary orderSummary) {
        return OrderSummaryDTO.builder().orderId(orderSummary.getOrderId())
                .customerId(orderSummary.getCustomerId())
                .invoiceAmount(orderSummary.getInvoiceAmount())
                .invoiceDate(orderSummary.getInvoiceDate() !=null ? orderSummary.getInvoiceDate().toLocalDate() : null)
                .paid(orderSummary.getPaid())
                .payMode(orderSummary.getPayMode())
                .purchaseItems(orderSummary.getPurchaseItems())
                .returnItems(orderSummary.getReturnItems())
                .build();
    }

    public static OrderSummary convertOrderSummaryDTOToDAO(OrderSummaryDTO orderSummaryDTO) {
        return OrderSummary.builder().orderId(orderSummaryDTO.getOrderId())
                .customerId(orderSummaryDTO.getCustomerId())
                .invoiceAmount(orderSummaryDTO.getInvoiceAmount())
                .invoiceDate(orderSummaryDTO.getInvoiceDate() !=null ? orderSummaryDTO.getInvoiceDate().atStartOfDay() : null)
                .paid(orderSummaryDTO.getPaid())
                .payMode(orderSummaryDTO.getPayMode())
                .purchaseItems(orderSummaryDTO.getPurchaseItems())
                .returnItems(orderSummaryDTO.getReturnItems())
                .build();
    }

    public static int getCustomisedOrderId(){
        LocalDate localDate = LocalDate.now();
        String customisedUniqueId = (localDate.format(DateTimeFormatter.ISO_DATE).substring(4)) +
                ((System.currentTimeMillis()+"").substring(9));
        return Integer.parseInt(customisedUniqueId.replace("-",""));
    }
}
