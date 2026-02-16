package com.gonzalodev.InventoryMgtSystem.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gonzalodev.InventoryMgtSystem.enums.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int status;
    private String message;
    // for login
    private String token;
    private UserRole role;
    private String expirationTime;
    private final LocalDateTime timestamp = LocalDateTime.now();

    // for pagination
    private Integer totalPages;
    private Long totalElements;

    // data output optionals
    private UserDTO user;
    private List<UserDTO> users;

    private CategoryDTO category;
    private List<CategoryDTO> categories;

    private ProductDTO product;
    private List<ProductDTO> products;

    private TransactionDTO transaction;
    private List<TransactionDTO> transactions;

    private SupplierDTO supplier;
    private List<SupplierDTO> suppliers;

}
