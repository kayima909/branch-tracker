package com.branchtracker.branchtracker.controllers;

import com.branchtracker.branchtracker.entity.User;
import com.branchtracker.branchtracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Get all users — HQ_ADMIN only
    @GetMapping
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get a user by ID — HQ_ADMIN only
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Update a user — HQ_ADMIN only
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // Deactivate a user — HQ_ADMIN only
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok(Map.of("message", "User deactivated successfully"));
    }

    // Change password — HQ_ADMIN only
    @PatchMapping("/{id}/password")
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<?> changePassword(@PathVariable Long id,
                                            @RequestBody Map<String, String> request) {
        userService.changePassword(id, request.get("newPassword"));
        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }
}

