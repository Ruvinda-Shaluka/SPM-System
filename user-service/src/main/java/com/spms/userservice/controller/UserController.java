package com.spms.userservice.controller;

import com.spms.userservice.model.User;
import com.spms.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody Map<String, String> credentials) {
        User user = userService.authenticateUser(credentials.get("email"), credentials.get("password"));
        if (user != null) {
            return ResponseEntity.ok("Authentication successful for user ID: " + user.getId());
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        User user = userService.getUserProfile(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUserProfile(id, user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<String> getHistory(@PathVariable Long id) {
        String history = userService.getBookingHistory(id);
        return history != null ? ResponseEntity.ok(history) : ResponseEntity.notFound().build();
    }
}