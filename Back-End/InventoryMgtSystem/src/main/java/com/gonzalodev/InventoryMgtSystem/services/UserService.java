package com.gonzalodev.InventoryMgtSystem.services;

import com.gonzalodev.InventoryMgtSystem.dtos.*;
import com.gonzalodev.InventoryMgtSystem.models.User;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAll();
    User getCurrentLoggedInUser();
    Response getUserById(Long id);
    Response update(Long id, UserUpdateDTO userDTO);
    Response updateRol(Long id, UpdateRolDTO updateRolDTO);
    Response delete(Long id);
    Response getUserTransactions(Long id);

}
