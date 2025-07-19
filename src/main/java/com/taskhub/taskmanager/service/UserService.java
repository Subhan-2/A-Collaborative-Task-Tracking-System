package com.taskhub.taskmanager.service;

import com.taskhub.taskmanager.dto.UserRegisterRequest;
import com.taskhub.taskmanager.dto.UserResponse;
import com.taskhub.taskmanager.entity.User;
import com.taskhub.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse registerUser(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }
}
