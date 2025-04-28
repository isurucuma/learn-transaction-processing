package isurucuma.learn.transaction_processing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateProductRequestDto {
    private String name;
    private double price;
    private int quantity;
}
