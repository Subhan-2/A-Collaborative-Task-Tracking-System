package com.taskhub.taskmanager.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
}
