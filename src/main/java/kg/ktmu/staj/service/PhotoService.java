package kg.ktmu.staj.service;

import kg.ktmu.staj.entity.Photo;
import kg.ktmu.staj.repo.PhotoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepo repo;

    public PhotoService(PhotoRepo repo) {
        this.repo = repo;
    }

    public List<Photo> findAll() {
        return repo.findAll();
    }
}
