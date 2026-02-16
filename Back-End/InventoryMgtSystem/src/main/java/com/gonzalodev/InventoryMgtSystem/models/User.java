package com.gonzalodev.InventoryMgtSystem.models;

import com.gonzalodev.InventoryMgtSystem.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = " Email is required")
     @Column(unique = true)
     @Email
     private String email;

    @NotBlank(message = "Password is required")
     private String password;

    @NotBlank(message = "Phone number is required")
     @Column(name = "phone_number")
     private String phoneNumber;

    @Column(name = "created_at")
     private LocalDateTime createdAt = LocalDateTime.now();

     @Enumerated(EnumType.STRING)
     private UserRole role;

     @OneToMany(mappedBy = "user", targetEntity = Transaction.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
     private List<Transaction> transactions;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt=" + createdAt +
                ", role=" + role +
                '}';
    }
}
