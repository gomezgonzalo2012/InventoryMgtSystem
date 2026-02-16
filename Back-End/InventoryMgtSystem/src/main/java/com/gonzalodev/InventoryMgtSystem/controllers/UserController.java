package com.gonzalodev.InventoryMgtSystem.controllers;

import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.dtos.UpdateRolDTO;
import com.gonzalodev.InventoryMgtSystem.dtos.UserDTO;
import com.gonzalodev.InventoryMgtSystem.dtos.UserUpdateDTO;
import com.gonzalodev.InventoryMgtSystem.models.User;
import com.gonzalodev.InventoryMgtSystem.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserByIf(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Response> getUserAndTransactions(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserTransactions(id));
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody UserUpdateDTO userDTO){
        return ResponseEntity.ok(userService.update(id, userDTO));
    }
    @PatchMapping("/update-rol/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRol(@PathVariable Long id, @RequestBody UpdateRolDTO updateRolDTO){
        return ResponseEntity.ok(userService.updateRol(id, updateRolDTO));
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> delete(@PathVariable Long id){
        return ResponseEntity.ok(userService.delete(id));
    }
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentLoggedInUser());
    }
}
