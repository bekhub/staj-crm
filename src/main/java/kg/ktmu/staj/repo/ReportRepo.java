package kg.ktmu.staj.repo;

import kg.ktmu.staj.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepo extends JpaRepository<Report, Long> {
}
