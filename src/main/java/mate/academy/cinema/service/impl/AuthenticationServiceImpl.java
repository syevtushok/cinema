package mate.academy.cinema.service.impl;

import mate.academy.cinema.exceptions.AuthenticationException;
import mate.academy.cinema.lib.Inject;
import mate.academy.cinema.lib.Service;
import mate.academy.cinema.model.User;
import mate.academy.cinema.service.AuthenticationService;
import mate.academy.cinema.service.ShoppingCartService;
import mate.academy.cinema.service.UserService;
import mate.academy.cinema.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    UserService userService;
    @Inject
    ShoppingCartService shoppingCartService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email);
        if (user != null && user.getPassword().equals(HashUtil.hashPassword(password,
                user.getSalt()))) {
            return user;
        }
        throw new AuthenticationException("Cannot Sign In with email " + email);
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        User userFromDataBase = userService.add(user);
        shoppingCartService.registerNewShoppingCart(userFromDataBase);
        return userFromDataBase;
    }
}
