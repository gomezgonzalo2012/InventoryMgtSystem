package com.gonzalodev.InventoryMgtSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "SKU is required")
    private String sku;

    @NotNull(message = "Price is required")
    @Positive
    private BigDecimal price;

    @NotNull(message = "stockQuantity must be greater than cero")
    @Positive(message = "stockQuantity must be a positive value")
    private Integer stockQuantity;

    private String description;
    private LocalDateTime expiryDate;
    private String imgUrl;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", imgUrl='" + imgUrl + '\'' +
                ", expiryDate=" + expiryDate +
                ", createdAt=" + createdAt +
                '}';
    }
}
