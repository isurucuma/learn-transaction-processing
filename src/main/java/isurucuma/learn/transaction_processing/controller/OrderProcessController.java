package isurucuma.learn.transaction_processing.controller;

import isurucuma.learn.transaction_processing.common.ApiResponse;
import isurucuma.learn.transaction_processing.dto.OrderRequestDto;
import isurucuma.learn.transaction_processing.service.OrderProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/order")
public class OrderProcessController {
    private final OrderProcessService orderProcessService;

    public OrderProcessController(OrderProcessService orderProcessService) {
        this.orderProcessService = orderProcessService;
    }

    @PostMapping
    public ApiResponse<String> processOrder(@RequestBody OrderRequestDto orderRequest) {
        // Logic to process the order
        return new ApiResponse<>("Success, order processed!", "200", "ProductId " + orderRequest.getProductId() + " and Quantity " + orderRequest.getQuantity());
    }

    @GetMapping
    public ApiResponse<String> getOrder() {
        // Logic to get the order
        return new ApiResponse<>("Success, order retrieved!", "200", "Order details retrieved successfully!");
    }
}
