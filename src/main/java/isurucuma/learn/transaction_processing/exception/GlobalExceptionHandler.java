package isurucuma.learn.transaction_processing.exception;

import isurucuma.learn.transaction_processing.common.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CreateOrderException.class)
    public ApiResponse<String> handleCreateOrderException(CreateOrderException e) {
        return new ApiResponse<>("Error", "400", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleGeneralException(Exception e) {
        return new ApiResponse<>("Error", "500", "An unexpected error occurred: " + e.getMessage());
    }
}
