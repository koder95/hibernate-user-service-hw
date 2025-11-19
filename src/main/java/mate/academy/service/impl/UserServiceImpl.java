package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.AuthenticationUtil;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        byte[] salt = AuthenticationUtil.generateSalt();
        user.setSalt(salt);
        user.setPassword(AuthenticationUtil.hashPassword(user.getPassword(), salt));
        return userDao.persist(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
