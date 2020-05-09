package kg.ktmu.staj.service;

import kg.ktmu.staj.repo.MeasurementRepo;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

    private final MeasurementRepo repo;

    public MeasurementService(MeasurementRepo repo) {
        this.repo = repo;
    }
}
