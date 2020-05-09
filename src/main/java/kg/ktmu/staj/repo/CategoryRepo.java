package kg.ktmu.staj.repo;

import kg.ktmu.staj.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
