package com.bestimol.service;

import com.bestimol.SaveEntityResponse;
import com.bestimol.dto.request.user.UserCreateRequestDTO;
import com.bestimol.dto.response.user.UserResponseDTO;
import com.bestimol.model.User;
import com.bestimol.repository.UserRepository;
import com.bestimol.dto.request.user.UserUpdateRequestDTO;
import com.bestimol.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public SaveEntityResponse createUser(UserCreateRequestDTO userDTO) {
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

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }

    public SaveEntityResponse updateUser(UserUpdateRequestDTO userDTO) {
        SaveEntityResponse response = new SaveEntityResponse();
        try {
            User user = userRepository.findById(userDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userDTO.getId()));
            mapUserCreateRequestDTOToUser(userDTO, user);
            User updatedUser = userRepository.save(user);
            response.setSuccess(true);
            response.setMessage("User updated successfully");
            response.setId(updatedUser.getId());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    public SaveEntityResponse deleteUser(Long id) {
        SaveEntityResponse response = new SaveEntityResponse();
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            userRepository.deleteById(id);
            response.setSuccess(true);
            response.setMessage("User deleted successfully");
            response.setId(id);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    private void mapUserCreateRequestDTOToUser(UserCreateRequestDTO userDTO, User user) {
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setIsVerified(userDTO.getIsVerified());
    }

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setIsVerified(user.getIsVerified());
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;
    }
}