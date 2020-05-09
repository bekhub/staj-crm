package kg.ktmu.staj.repo;

import kg.ktmu.staj.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
