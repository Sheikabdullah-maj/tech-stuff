package learnings.cassandra.service;

import learnings.cassandra.dao.OrderSummary;
import learnings.cassandra.dto.OrderSummaryDTO;
import learnings.cassandra.repository.OrderSummaryRepository;
import learnings.cassandra.util.OrderSummaryUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderSummaryService {
    private final OrderSummaryRepository orderSummaryRepository;

    public OrderSummaryDTO saveOrderSummary(OrderSummaryDTO orderSummaryDTO) {

        if (Objects.isNull(orderSummaryDTO.getOrderId())) {
            log.info("Request is for Insertion");
            orderSummaryDTO.setOrderId(OrderSummaryUtils.getCustomisedOrderId());
        }
        OrderSummary orderSummary = OrderSummaryUtils.convertOrderSummaryDTOToDAO(orderSummaryDTO);
        orderSummary = orderSummaryRepository.save(orderSummary);
        return OrderSummaryUtils.convertOrderSummaryDAOToDTO(orderSummary);
    }

    public OrderSummaryDTO getOrderSummary(int orderId) {
        Optional<OrderSummary> optionalOrderSummary =  orderSummaryRepository.findById(orderId);
        OrderSummary orderSummary =  optionalOrderSummary.orElse(new OrderSummary());
        return OrderSummaryUtils.convertOrderSummaryDAOToDTO(orderSummary);
    }

    public void deleteOrderSummary(int orderId) {
        orderSummaryRepository.deleteById(orderId);
        log.info("Order summary deleted! {}", orderId);
    }
}
