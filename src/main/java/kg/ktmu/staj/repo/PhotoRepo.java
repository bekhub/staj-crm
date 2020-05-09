package kg.ktmu.staj.repo;

import kg.ktmu.staj.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<Photo, Long> {
}
