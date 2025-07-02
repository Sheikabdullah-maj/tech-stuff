package learnings.cassandra.controller;


import learnings.cassandra.dto.OrderSummaryDTO;
import learnings.cassandra.service.OrderSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderSummary")
@RequiredArgsConstructor
public class OrderSummaryController {

    private final OrderSummaryService orderSummaryService;

    @PostMapping("save")
    public OrderSummaryDTO saveOrder(@RequestBody OrderSummaryDTO requestDTO){
        return orderSummaryService.saveOrderSummary(requestDTO);
    }

    @GetMapping("get/{orderId}")
    public OrderSummaryDTO getOrderSummary(@PathVariable Integer orderId) {
     return orderSummaryService.getOrderSummary(orderId);
    }

    @DeleteMapping("delete/{orderId}")
    public void deleteOrderSummary(@PathVariable Integer orderId) {
        orderSummaryService.deleteOrderSummary(orderId);
    }
}
