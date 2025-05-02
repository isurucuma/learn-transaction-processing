package isurucuma.learn.transaction_processing.service;

import isurucuma.learn.transaction_processing.dto.CreateProductRequestDto;
import isurucuma.learn.transaction_processing.dto.OrderRequestDto;
import isurucuma.learn.transaction_processing.dto.OrderResponseDto;
import isurucuma.learn.transaction_processing.dto.ProductResponseDto;
import isurucuma.learn.transaction_processing.entity.Order;
import isurucuma.learn.transaction_processing.exception.CreateOrderException;
import isurucuma.learn.transaction_processing.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderProcessService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderProcessService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
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

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public OrderResponseDto createOrder(OrderRequestDto orderRequest) throws CreateOrderException {
        ProductResponseDto product = productService.getProductById(orderRequest.getProductId());
        if (product.getQuantity() < orderRequest.getQuantity()) {
            throw new CreateOrderException("Insufficient stock for product ID: " + orderRequest.getProductId());
        }
        Order order = new Order();
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(calculateTotalPrice(orderRequest.getProductId(), orderRequest.getQuantity()));


        // need to update the product stock
        product.setQuantity(product.getQuantity() - orderRequest.getQuantity());
        var updatedProductDetails = new CreateProductRequestDto();
        updatedProductDetails.setName(product.getName());
        updatedProductDetails.setPrice(product.getPrice());
        updatedProductDetails.setQuantity(product.getQuantity());

        // these two operations should be handled in a single transaction
        // otherwise if one fails, the other will not be rolled back
        Order savedOrder = orderRepository.save(order);
        productService.updateProduct(product.getId(), updatedProductDetails);

        return mapToDto(savedOrder);
    }

    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderRequest) {
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
