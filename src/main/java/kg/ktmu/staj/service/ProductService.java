package kg.ktmu.staj.service;

import kg.ktmu.staj.entity.Product;
import kg.ktmu.staj.repo.CategoryRepo;
import kg.ktmu.staj.repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }
}
