package com.example.km.controller;

import com.example.km.security.CustomOAuth2User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * User Controller for OAuth2 user info
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal CustomOAuth2User user) {
        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            response.put("id", user.getUserId());
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("name", user.getUser().getName());
            response.put("avatar", user.getUser().getAvatar());
            response.put("provider", user.getUser().getProvider());
            response.put("authenticated", true);
        } else {
            response.put("authenticated", false);
            response.put("message", "User not authenticated");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile(@AuthenticationPrincipal CustomOAuth2User user) {
        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            response.put("user", user.getUser());
            response.put("timestamp", LocalDateTime.now());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Unauthorized");
            response.put("message", "Please login first");
            return ResponseEntity.status(401).body(response);
        }
    }
}
