package com.taskhub.taskmanager.config;

import com.taskhub.taskmanager.repository.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.*;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

