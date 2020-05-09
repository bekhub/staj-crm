package kg.ktmu.staj.repo;

import kg.ktmu.staj.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepo extends JpaRepository<Measurement, Long> {
}
