package com.taskhub.taskmanager.controller;

import com.taskhub.taskmanager.dto.*;
import com.taskhub.taskmanager.entity.User;
import com.taskhub.taskmanager.repository.UserRepository;
import com.taskhub.taskmanager.security.JwtService;
import com.taskhub.taskmanager.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // ========================
    // Register Endpoint
    // ========================
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody  UserRegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    // ========================
    // Login Endpoint
    // ========================
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String jwtToken = jwtService.generateToken(user.getEmail());

            return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthenticationResponse("Invalid credentials"));
        }
    }
}
