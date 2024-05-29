package com.bestimol.service;

import com.bestimol.SaveEntityResponse;
import com.bestimol.dto.request.auth.AuthRequest;
import com.bestimol.dto.request.auth.SingUpRequest;
import com.bestimol.dto.response.auth.TokenResponseDto;
import com.bestimol.dto.request.auth.LoginRequest;
import com.bestimol.model.User;
import com.bestimol.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public SaveEntityResponse singUp(SingUpRequest userDTO) {
        SaveEntityResponse response = new SaveEntityResponse();
        try {
            User user = new User();
            mapUserCreateRequestDTOToUser(userDTO, user);
            user.setCreatedAt(LocalDateTime.now());
            User savedUser = userRepository.save(user);
            response.setSuccess(true);
            response.setMessage("User created successfully");
            response.setId(savedUser.getId());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    private void mapUserCreateRequestDTOToUser(SingUpRequest requestDTO, User user) {
        user.setUsername(requestDTO.getUsername());
        user.setEmail(requestDTO.getEmail());
        user.setPhoneNumber(requestDTO.getPhoneNumber());
        user.setIsVerified(requestDTO.getIsVerified());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setAuthorities(requestDTO.getAuthorities());
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setIsDeleted(false);
        user.setEnabled(true);
    }

    public TokenResponseDto login(LoginRequest loginRequest) {

        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if (auth.isAuthenticated()) {
            return TokenResponseDto
                    .builder()
                    .accessToken(jwtService.generateToken(loginRequest.getUsername()))
                    .build();
        } else {
            throw new UsernameNotFoundException("user not found");
        }
    }

    public String generateToken(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.getUsername());
        }
        log.info("invalid username " + request.getUsername());
        throw new UsernameNotFoundException("invalid username {} " + request.getUsername());
    }
}