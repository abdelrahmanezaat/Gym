package com.gym.gym.controllers;

import com.gym.gym.models.User;
import com.gym.gym.repo.UserRepository;
import com.gym.gym.security.JwtTokenProvider;
import com.gym.gym.services.UserDetailsServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    @Autowired
    UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    

    // SignUp API (Register User)
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody User user) {
        // Validate the password field
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be null or empty");
        }

        // Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user into the database
        userDetailsServiceImpl.saveUser(user);

        return ResponseEntity.ok("User registered successfully");
    }

    // SignIn API (Login User)
    @PostMapping("/signin")
public ResponseEntity<?> signIn(@RequestBody User loginRequest) {
    try {
        // Validate email and password fields
        if (loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be null or empty");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be null or empty");
        }

        // Find user by email
        User user = repository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        // Log user details for debugging
        System.out.println("User found: " + user.getEmail());
        System.out.println("User roles: " + user.getRoles());

        // Compare the entered password with the encoded password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        // Extract the roles and generate the JWT token
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getId(),roles);

        // Prepare response with JWT token
        Map<String, String> response = new HashMap<>();
        response.put("token", "Bearer " + token);  // Add "Bearer" prefix to the token

        // Return response with JWT token
        return ResponseEntity.ok(response);

    } catch (BadCredentialsException e) {
        // In case of failed authentication (wrong email or password)
        return ResponseEntity.status(401).body("Invalid email or password");
    } catch (Exception e) {
        // In case of any other errors
        return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
    }
}

}
