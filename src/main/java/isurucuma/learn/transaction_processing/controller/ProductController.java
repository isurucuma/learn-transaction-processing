package isurucuma.learn.transaction_processing.controller;

import isurucuma.learn.transaction_processing.common.ApiResponse;
import isurucuma.learn.transaction_processing.dto.CreateProductRequestDto;
import isurucuma.learn.transaction_processing.dto.ProductResponseDto;
import isurucuma.learn.transaction_processing.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ApiResponse<List<ProductResponseDto>> getAllProducts() {
        // Logic to get the product
        return new ApiResponse<>("Success, products retrieved!", "200", List.of(new ProductResponseDto(1L, "Product1", 100.0, 1)));
    }

    @GetMapping(path = "/{productId}")
    public ApiResponse<ProductResponseDto> getProductById(@PathVariable Long productId) {
        // Logic to get the product by ID
        return new ApiResponse<>("Success, product retrieved!", "200", new ProductResponseDto(productId, "Product" + productId, 100.0, 1));
    }

    @PostMapping
    public ApiResponse<ProductResponseDto> createProduct(@RequestBody CreateProductRequestDto productRequest) {
        // Logic to create a new product
        return new ApiResponse<>("Success, product created!", "201", new ProductResponseDto(1L, productRequest.getName(), productRequest.getPrice(), 1  ));
    }
}
