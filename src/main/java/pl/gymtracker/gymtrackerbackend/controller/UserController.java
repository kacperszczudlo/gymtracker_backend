package pl.gymtracker.gymtrackerbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gymtracker.gymtrackerbackend.dto.UserDto;
import pl.gymtracker.gymtrackerbackend.model.User;
import pl.gymtracker.gymtrackerbackend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(userDto.getPasswordHash());
        user.setGender(userDto.getGender());
        user.setHeight(userDto.getHeight());

        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }
}
