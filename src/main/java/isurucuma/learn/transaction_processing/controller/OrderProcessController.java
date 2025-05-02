package isurucuma.learn.transaction_processing.controller;

import isurucuma.learn.transaction_processing.common.ApiResponse;
import isurucuma.learn.transaction_processing.dto.OrderRequestDto;
import isurucuma.learn.transaction_processing.dto.OrderResponseDto;
import isurucuma.learn.transaction_processing.exception.CreateOrderException;
import isurucuma.learn.transaction_processing.service.OrderProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/order")
public class OrderProcessController {
    private final OrderProcessService orderProcessService;

    public OrderProcessController(OrderProcessService orderProcessService) {
        this.orderProcessService = orderProcessService;
    }

    @PostMapping
    public ApiResponse<String> processOrder(@RequestBody OrderRequestDto orderRequest) throws CreateOrderException {
        // Logic to process the order
        OrderResponseDto orderResponse = orderProcessService.createOrder(orderRequest);
        return new ApiResponse<>("Success, order processed!", "200", "Order processed for productID: " + orderResponse.getProductId() + ", with quantity: " + orderResponse.getQuantity() + " and total price: " + orderResponse.getTotalPrice());
    }

    @GetMapping
    public ApiResponse<List<OrderResponseDto>> getAllOrders() {
        // Logic to get the order
        List<OrderResponseDto> orders = orderProcessService.getAllOrders();
        return new ApiResponse<List<OrderResponseDto>>("Success, order retrieved!", "200", orders);
    }
}
