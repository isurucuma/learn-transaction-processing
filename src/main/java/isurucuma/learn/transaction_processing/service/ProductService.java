package isurucuma.learn.transaction_processing.service;

import isurucuma.learn.transaction_processing.dto.CreateProductRequestDto;
import isurucuma.learn.transaction_processing.dto.ProductResponseDto;
import isurucuma.learn.transaction_processing.entity.Product;
import isurucuma.learn.transaction_processing.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDto(product);
    }

    public ProductResponseDto createProduct(CreateProductRequestDto productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getQuantity());
        Product savedProduct = productRepository.save(product);
        return mapToDto(savedProduct);
    }

    public ProductResponseDto updateProduct(Long productId, CreateProductRequestDto productRequest) {
        throw new RuntimeException("DB error");
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//        product.setName(productRequest.getName());
//        product.setPrice(productRequest.getPrice());
//        product.setStockQuantity(productRequest.getQuantity());
//        Product updatedProduct = productRepository.save(product);
//        return mapToDto(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(productId);
    }

    private ProductResponseDto mapToDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }

}
