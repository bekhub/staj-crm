package kg.ktmu.staj.service;

import kg.ktmu.staj.repo.PhotoRepo;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    private final PhotoRepo repo;

    public PhotoService(PhotoRepo repo) {
        this.repo = repo;
    }
}
