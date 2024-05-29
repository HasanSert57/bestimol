package com.bestimol.controller;

import com.bestimol.SaveEntityResponse;
import com.bestimol.dto.request.auth.AuthRequest;
import com.bestimol.dto.response.user.UserResponseDTO;
import com.bestimol.dto.request.user.UserCreateRequestDTO;
import com.bestimol.dto.request.user.UserUpdateRequestDTO;
import com.bestimol.service.JwtService;
import com.bestimol.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/all")
    @Operation(summary = "Get all users")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/update")
    @Operation(summary = "Update a user")
    public ResponseEntity<SaveEntityResponse> updateUser(@RequestBody UserUpdateRequestDTO userDTO) {
        SaveEntityResponse response = userService.updateUser(userDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a user")
    public ResponseEntity<SaveEntityResponse> deleteUser(@PathVariable Long id) {
        SaveEntityResponse response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }
}