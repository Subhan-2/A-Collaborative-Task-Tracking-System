package com.taskhub.taskmanager.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    // Optional: role field (simplified, or you can use Enum if you prefer)
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can implement authorities from the role string if needed
        return Collections.emptyList(); // or return List.of(new SimpleGrantedAuthority("ROLE_USER"))
    }

    @Override
    public String getUsername() {
        return email; // Spring Security uses this to identify the user
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // change if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // change if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // change if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // change if needed
    }
}
