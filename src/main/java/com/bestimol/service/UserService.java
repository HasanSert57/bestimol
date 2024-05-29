package com.bestimol.service;

import com.bestimol.SaveEntityResponse;
import com.bestimol.dto.request.user.UserCreateRequestDTO;
import com.bestimol.dto.response.user.UserResponseDTO;
import com.bestimol.model.Role;
import com.bestimol.model.User;
import com.bestimol.repository.UserRepository;
import com.bestimol.dto.request.user.UserUpdateRequestDTO;
import com.bestimol.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    private void mapUserCreateRequestDTOToUser(UserCreateRequestDTO requestDTO, User user) {
        user.setUsername(requestDTO.getUserName());
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

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setIsVerified(user.getIsVerified());
        userDTO.setCreatedAt(user.getCreatedAt());
        Set<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        userDTO.setAuthorities(roles);

        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }
}