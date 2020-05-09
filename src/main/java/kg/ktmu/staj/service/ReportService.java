package kg.ktmu.staj.service;

import kg.ktmu.staj.repo.ReportRepo;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepo repo;

    public ReportService(ReportRepo repo) {
        this.repo = repo;
    }
}
