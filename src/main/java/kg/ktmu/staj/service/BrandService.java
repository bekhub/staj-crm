package kg.ktmu.staj.service;

import kg.ktmu.staj.repo.BrandRepo;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    private final BrandRepo repo;

    public BrandService(BrandRepo repo) {
        this.repo = repo;
    }
}
