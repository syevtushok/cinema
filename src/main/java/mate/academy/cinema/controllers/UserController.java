package mate.academy.cinema.controllers;

import mate.academy.cinema.dto.request.UserRequestDto;
import mate.academy.cinema.dto.response.UserResponseDto;
import mate.academy.cinema.model.User;
import mate.academy.cinema.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        userService.add(user);
        return getUserResponseDto(user);
    }

    @GetMapping("/byemail{email}")
    public UserResponseDto getUserByEmail(@PathVariable String email) {
        return getUserResponseDto(userService.findByEmail(email));
    }

    private UserResponseDto getUserResponseDto(User user) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
