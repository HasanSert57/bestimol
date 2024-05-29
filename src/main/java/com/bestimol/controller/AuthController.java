package com.bestimol.controller;

import com.bestimol.SaveEntityResponse;
import com.bestimol.dto.request.auth.AuthRequest;
import com.bestimol.dto.request.auth.SingUpRequest;
import com.bestimol.dto.response.auth.TokenResponseDto;
import com.bestimol.dto.request.auth.LoginRequest;
import com.bestimol.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<SaveEntityResponse> singUp(@RequestBody SingUpRequest singUpRequest){
        return ResponseEntity.ok(authService.singUp(singUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest request) {
        return authService.generateToken(request);
    }

    @GetMapping("/user")
    public String getUserString() {
        return "This is USER!";
    }

    @GetMapping("/admin")
    public String getAdminString() {
        return "This is ADMIN!";
    }

}