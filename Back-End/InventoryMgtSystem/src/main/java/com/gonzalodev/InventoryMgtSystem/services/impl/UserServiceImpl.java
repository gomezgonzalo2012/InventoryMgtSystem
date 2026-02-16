package com.gonzalodev.InventoryMgtSystem.services.impl;

import com.gonzalodev.InventoryMgtSystem.dtos.*;
import com.gonzalodev.InventoryMgtSystem.enums.UserRole;
import com.gonzalodev.InventoryMgtSystem.exceptions.InvalidCredentialsException;
import com.gonzalodev.InventoryMgtSystem.exceptions.NotFoundException;
import com.gonzalodev.InventoryMgtSystem.models.Transaction;
import com.gonzalodev.InventoryMgtSystem.models.User;
import com.gonzalodev.InventoryMgtSystem.repositories.UserRepository;
import com.gonzalodev.InventoryMgtSystem.security.JwtService;
import com.gonzalodev.InventoryMgtSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @Override
    public Response registerUser(RegisterRequest registerRequest) {
        UserRole role = UserRole.MANAGER;

        if(registerRequest.getRole()!= null){
            role = registerRequest.getRole();
        }

        User userToSave = User.builder()
                        .name(registerRequest.getName())
                        .email(registerRequest.getEmail())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .phoneNumber(registerRequest.getPhoneNumber())
                        .role(role)
                        .build();
        userRepository.save(userToSave);
        return Response.builder()
                .status(204)
                .message("User successfully registered")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new NotFoundException("User not found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password doesn't match");
        }
        String token = jwtService.generateToken(loginRequest.getEmail());
        return Response.builder()
                .status(200)
                .message("User logged in successfully")
                .token(token)
                .role(user.getRole())
                .expirationTime("2 weeks")
                .build();
    }

    @Override
    public Response getAll() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        users.forEach(user -> user.setTransactions(null));
        List<UserDTO> userDtos = modelMapper.map(users, new TypeToken<List<UserDTO>>(){}.getType());
        return Response.builder()
                .status(200)
                .message("success")
                .users(userDtos)
                .build();
    }

    @Override
    public User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw  new NotFoundException("User not found");
        }
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setTransactions(null);
        return user;
    }

    @Override
    public Response getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return  Response.builder()
                .status(200)
                .message("success")
                .user(userDTO)
                .build();
    }

    @Override
    public Response update(Long id, UserUpdateDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (userDTO.getName() != null)  existingUser.setName(userDTO.getName());
        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(userDTO.getPhoneNumber());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(existingUser);
        return Response.builder()
                .status(204)
                .build();
    }
    @Override
    public Response updateRol(Long id, UpdateRolDTO updateRolDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        User currentUser = getCurrentLoggedInUser();
        if(currentUser.getRole()!= UserRole.ADMIN){
            throw new AccessDeniedException("Not enough permissions");
        }
        if(updateRolDTO.getRole()!= null) existingUser.setRole(updateRolDTO.getRole());

        userRepository.save(existingUser);
        return Response.builder()
                .status(200)
                .message("User role updated successfully ")
                .build();
    }

    @Override
    public Response delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.deleteById(id);
        return  Response.builder()
                .status(200)
                .message("User successfully deleted")
                .build();
    }

    @Override
    public Response getUserTransactions(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.getTransactions().forEach(transactionDTO -> {
            transactionDTO.setUser(null);
            transactionDTO.setSupplier(null);
        });
        return  Response.builder()
                .status(200)
                .message("User successfully deleted")
                .user(userDTO)
                .build();
    }
}
