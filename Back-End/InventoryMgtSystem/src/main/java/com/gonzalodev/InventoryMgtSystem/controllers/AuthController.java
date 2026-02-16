package com.gonzalodev.InventoryMgtSystem.controllers;

import com.gonzalodev.InventoryMgtSystem.dtos.LoginRequest;
import com.gonzalodev.InventoryMgtSystem.dtos.RegisterRequest;
import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody @Valid RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginRequest loginRequest){
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}
