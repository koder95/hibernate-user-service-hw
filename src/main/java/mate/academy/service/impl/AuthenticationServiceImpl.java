package mate.academy.service.impl;

import java.util.Objects;
import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.AuthenticationUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        String msg = "User email or password is wrong";
        if (optionalUser.isEmpty() || !Objects.equals(
                optionalUser.get().getPassword(),
                AuthenticationUtil.hashPassword(password, optionalUser.get().getSalt())
        )) {
            throw new AuthenticationException(msg);
        }
        return optionalUser.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        String msg = "Cannot register an email: " + email;
        if (optionalUser.isPresent()) {
            throw new RegistrationException(msg);
        }
        User created = new User();
        created.setEmail(email);
        created.setPassword(password);
        return userService.add(created);
    }
}
