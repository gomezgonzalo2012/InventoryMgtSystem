package com.gonzalodev.InventoryMgtSystem.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private Long id;
    private Long supplierId;
    private Long categoryId;
    private Long productId;

    private String description;
    private String name;
    private String sku;
    private BigDecimal price;
    private Integer stockQuantity;

    private LocalDateTime createdAt;
    private LocalDateTime expiryDate;
    private String imgUrl;

}
