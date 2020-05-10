package kg.ktmu.staj.service;

import kg.ktmu.staj.entity.Report;
import kg.ktmu.staj.repo.ReportRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepo repo;

    public ReportService(ReportRepo repo) {
        this.repo = repo;
    }

    public List<Report> findAll() {
        return repo.findAll();
    }
}
