package com.bestimol.controller;

import com.bestimol.SaveEntityResponse;
import com.bestimol.dto.response.user.UserResponseDTO;
import com.bestimol.dto.request.user.UserCreateRequestDTO;
import com.bestimol.dto.request.user.UserUpdateRequestDTO;
import com.bestimol.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Create a user")
    public ResponseEntity<SaveEntityResponse> createUser(@RequestBody UserCreateRequestDTO userDTO) {
        SaveEntityResponse response = userService.createUser(userDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
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

    @PutMapping
    @Operation(summary = "Update a user")
    public ResponseEntity<SaveEntityResponse> updateUser(@RequestBody UserUpdateRequestDTO userDTO) {
        SaveEntityResponse response = userService.updateUser(userDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public ResponseEntity<SaveEntityResponse> deleteUser(@PathVariable Long id) {
        SaveEntityResponse response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }
}