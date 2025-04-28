package isurucuma.learn.transaction_processing.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private String status;
    private T data;
}
