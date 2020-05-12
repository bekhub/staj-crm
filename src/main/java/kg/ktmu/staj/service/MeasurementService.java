package kg.ktmu.staj.service;

import kg.ktmu.staj.entity.Measurement;
import kg.ktmu.staj.repo.MeasurementRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {

    private final MeasurementRepo repo;

    public MeasurementService(MeasurementRepo repo) {
        this.repo = repo;
    }

    public List<Measurement> findAll() {
        return repo.findAll();
    }

    public void save(Measurement measurement) {
        repo.save(measurement);
    }

    public void delete(Measurement measurement) {
        repo.delete(measurement);
    }
}
