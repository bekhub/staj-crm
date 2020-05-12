package kg.ktmu.staj.service;

import kg.ktmu.staj.entity.Brand;
import kg.ktmu.staj.repo.BrandRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepo repo;

    public BrandService(BrandRepo repo) {
        this.repo = repo;
    }

    public List<Brand> findAll() {
        return repo.findAll();
    }

    public void save(Brand brand) {
        repo.save(brand);
    }

    public void delete(Brand brand) {
        repo.delete(brand);
    }
}
