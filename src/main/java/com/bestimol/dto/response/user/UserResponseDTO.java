package com.bestimol.dto.response.user;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private Boolean isVerified;
    private LocalDateTime createdAt;
    private Set<String> authorities;
}