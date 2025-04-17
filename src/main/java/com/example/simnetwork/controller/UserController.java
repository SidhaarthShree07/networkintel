package com.example.simnetwork.controller;

import com.example.simnetwork.model.User;
import com.example.simnetwork.service.UserService;
import com.example.simnetwork.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "https:/", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Home page endpoint after login
    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> getHomePage(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.getUserByEmail(userPrincipal.getUsername());
        
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("isAuthenticated", true);
        response.put("redirectUrl", "http://localhost:3000/"); // React frontend dashboard URL
        response.put("message", "Welcome to your dashboard");
        
        return ResponseEntity.ok(response);
    }

    // ✅ Secure endpoint to get the currently logged-in user's info
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        // Use email from UserPrincipal to fetch user details
        User user = userService.getUserByEmail(userPrincipal.getUsername());  // Username is the email
        return ResponseEntity.ok(user);
    }

    // ✅ Admin-only: fetch all users
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ Admin-only: dummy dashboard route
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Welcome to Admin Dashboard");
    }
}
