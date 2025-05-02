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
        List<ProductResponseDto> products = productService.getAllProducts();
        return new ApiResponse<>("Success, products retrieved!", "200", products);
    }

    @GetMapping(path = "/{productId}")
    public ApiResponse<ProductResponseDto> getProductById(@PathVariable Long productId) {
        // Logic to get the product by ID
        ProductResponseDto product = productService.getProductById(productId);
        return new ApiResponse<>("Success, product retrieved!", "200", product);
    }

    @PostMapping
    public ApiResponse<ProductResponseDto> createProduct(@RequestBody CreateProductRequestDto productRequest) {
        // Logic to create a new product
        ProductResponseDto productResponse = productService.createProduct(productRequest);
        return new ApiResponse<>("Success, product created!", "201", productResponse);
    }
}
