package com.bestimol.dto.request.user;

import com.bestimol.model.Role;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDTO {
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private Boolean isVerified;
    Set<Role> authorities;
}