package isurucuma.learn.transaction_processing.service;

import isurucuma.learn.transaction_processing.dto.OrderResponseDto;
import isurucuma.learn.transaction_processing.entity.Order;
import isurucuma.learn.transaction_processing.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderProcessService {
    private final OrderRepository orderRepository;

    public OrderProcessService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToDto(order);
    }

    public OrderResponseDto createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(calculateTotalPrice(orderRequest.getProductId(), orderRequest.getQuantity()));
        Order savedOrder = orderRepository.save(order);
        return mapToDto(savedOrder);
    }

    public OrderResponseDto updateOrder(Long orderId, OrderRequest orderRequest) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(calculateTotalPrice(orderRequest.getProductId(), orderRequest.getQuantity()));
        Order updatedOrder = orderRepository.save(order);
        return mapToDto(updatedOrder);
    }

    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }

    private OrderResponseDto mapToDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getProductId(),
                order.getQuantity(),
                order.getTotalPrice()
        );
    }

    private double calculateTotalPrice(Long productId, int quantity) {
        // Placeholder logic for calculating total price
        // Replace with actual logic to fetch product price and calculate total
        double productPrice = 100.0; // Example price
        return productPrice * quantity;
    }
}
