package isurucuma.learn.transaction_processing.repo;

import isurucuma.learn.transaction_processing.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
