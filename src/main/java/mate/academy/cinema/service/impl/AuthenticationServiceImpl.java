package mate.academy.cinema.service.impl;

import mate.academy.cinema.exceptions.AuthenticationException;
import mate.academy.cinema.model.User;
import mate.academy.cinema.service.AuthenticationService;
import mate.academy.cinema.service.ShoppingCartService;
import mate.academy.cinema.service.UserService;
import mate.academy.cinema.util.HashUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(UserService userService,
                                     ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

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
