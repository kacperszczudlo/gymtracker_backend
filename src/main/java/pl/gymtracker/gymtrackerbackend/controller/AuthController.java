package pl.gymtracker.gymtrackerbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gymtracker.gymtrackerbackend.dto.LoginRequest;
import pl.gymtracker.gymtrackerbackend.dto.RegisterRequest;
import pl.gymtracker.gymtrackerbackend.dto.UserProfileResponse;
import pl.gymtracker.gymtrackerbackend.dto.UserProfileUpdateRequest;
import pl.gymtracker.gymtrackerbackend.entity.User;
import pl.gymtracker.gymtrackerbackend.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    // Ręczny konstruktor (bez Lomboka)
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserProfileResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/ping")
    public String ping() {
        return "Backend działa 👋";
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer id, @RequestBody UserProfileUpdateRequest request) {
        userService.updateUserProfile(id, request);
        return ResponseEntity.ok().build();
    }


}
