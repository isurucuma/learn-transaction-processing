package isurucuma.learn.transaction_processing.repo;

import isurucuma.learn.transaction_processing.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
