package mate.academy.cinema.controllers;

import javax.annotation.PostConstruct;

import mate.academy.cinema.model.Role;
import mate.academy.cinema.model.User;
import mate.academy.cinema.service.RoleService;
import mate.academy.cinema.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitializationController {
    private RoleService roleService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public InitializationController(RoleService roleService, UserService userService,
                                    PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void initialize() {
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        Role userRole = new Role();
        userRole.setRole("USER");
        roleService.add(adminRole);
        roleService.add(userRole);

        User user = new User();
        user.setEmail("serhii@gmail.com");
        user.setPassword(passwordEncoder.encode("a"));
        user.addRole(roleService.getRole("ADMIN"));
        user.addRole(roleService.getRole("USER"));
        userService.add(user);
    }
}
