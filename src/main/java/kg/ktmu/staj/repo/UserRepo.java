package kg.ktmu.staj.repo;

import kg.ktmu.staj.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
    User findByUsername(String username);

}
