package com.taskhub.taskmanager.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
}
