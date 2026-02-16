package com.gonzalodev.InventoryMgtSystem.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gonzalodev.InventoryMgtSystem.enums.TransactionStatus;
import com.gonzalodev.InventoryMgtSystem.enums.TransactionType;
import com.gonzalodev.InventoryMgtSystem.models.Product;
import com.gonzalodev.InventoryMgtSystem.models.Supplier;
import com.gonzalodev.InventoryMgtSystem.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {

    private Long id;

    private Integer totalProducts;

    private BigDecimal totalPrice;

    private TransactionType transactionType; // purchase, return m sale

    private TransactionStatus status; // pending, completed processing

    private String description;

    private String note;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Product product;

    private User user;

    private Supplier supplier;
}
