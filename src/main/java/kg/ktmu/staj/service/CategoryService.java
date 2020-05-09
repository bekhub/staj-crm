package kg.ktmu.staj.service;

import kg.ktmu.staj.repo.CategoryRepo;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepo repo;

    public CategoryService(CategoryRepo repo) {
        this.repo = repo;
    }
}
