package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.User;

public interface UserDao {
    Optional<User> findByEmail(String email);

    User persist(User user);
}
