package isurucuma.learn.transaction_processing.dto;

import lombok.Data;

@Data
public class OrderRequestDto {
    private Long productId;
    private int quantity;
}
