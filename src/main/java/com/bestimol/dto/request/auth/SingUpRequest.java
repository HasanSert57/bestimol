package com.bestimol.dto.request.auth;

import com.bestimol.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingUpRequest {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private Boolean isVerified;
    Set<Role> authorities;
}
