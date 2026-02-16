package com.gonzalodev.InventoryMgtSystem.security;

import com.gonzalodev.InventoryMgtSystem.exceptions.NotFoundException;
import com.gonzalodev.InventoryMgtSystem.models.User;
import com.gonzalodev.InventoryMgtSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).
                orElseThrow(() -> new NotFoundException("User not found"));
        return CustomUserDetails.builder()
                .user(user)
                .build();
    }
}
