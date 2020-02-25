package mate.academy.cinema.security;

import static org.springframework.security.core.userdetails.User.UserBuilder;
import static org.springframework.security.core.userdetails.User.withUsername;

import mate.academy.cinema.model.Role;
import mate.academy.cinema.model.User;
import mate.academy.cinema.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if (user != null) {
            UserBuilder builder = withUsername(email);
            builder.password(user.getPassword());
            builder.roles(getRoles(user));
            return builder.build();
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    private String[] getRoles(User user) {
        return user.getRoles().stream()
                .map(Role::getRole)
                .toArray(String[]::new);
    }
}
