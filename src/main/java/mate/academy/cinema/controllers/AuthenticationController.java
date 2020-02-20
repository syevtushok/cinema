package mate.academy.cinema.controllers;

import mate.academy.cinema.dto.request.UserRequestDto;
import mate.academy.cinema.exceptions.AuthenticationException;
import mate.academy.cinema.service.AuthenticationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthenticationController {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserRequestDto userRequestDto) {
        try {
            authenticationService.login(userRequestDto.getEmail(), userRequestDto.getPassword());
            return "Welcome, " + userRequestDto.getEmail() + "!";
        } catch (AuthenticationException e) {
            LOGGER.error("Cannot login user with email " + userRequestDto.getEmail()
                         + " and password " + userRequestDto.getPassword(), e);
            return "Please check your email or password";
        }
    }

    @PostMapping("/register")
    public void register(@RequestBody UserRequestDto userRequestDto) {
        authenticationService.register(userRequestDto.getEmail(), userRequestDto.getPassword());
    }
}
