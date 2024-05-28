package com.bestimol.dto.request.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDTO {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private Boolean isVerified;
}