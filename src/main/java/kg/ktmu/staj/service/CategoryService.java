package kg.ktmu.staj.service;

import kg.ktmu.staj.entity.Category;
import kg.ktmu.staj.repo.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepo repo;

    public CategoryService(CategoryRepo repo) {
        this.repo = repo;
    }

    public List<Category> findAll() {
        return repo.findAll();
    }
}
