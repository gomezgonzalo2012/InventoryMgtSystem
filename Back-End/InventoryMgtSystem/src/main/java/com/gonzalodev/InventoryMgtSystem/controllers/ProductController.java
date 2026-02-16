package com.gonzalodev.InventoryMgtSystem.controllers;

import com.gonzalodev.InventoryMgtSystem.dtos.ProductDTO;
import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")

    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> saveProduct(
            @RequestParam(value = "imageFile") MultipartFile imageFile,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "sku") String sku,
            @RequestParam(value = "price") BigDecimal price,
            @RequestParam(value = "stockQuantity") Integer stockQuantity,
            @RequestParam(value = "categoryId") Long categoryId,
            @RequestParam(value = "description", required = false) String description) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setSku(sku);
        productDTO.setPrice(price);
        productDTO.setStockQuantity(stockQuantity);
        productDTO.setCategoryId(categoryId);
        productDTO.setDescription(description);

        Response response = productService.saveProduct(productDTO, imageFile);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(
            @RequestParam(value = "imageFile") MultipartFile imageFile,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "sku") String sku,
            @RequestParam(value = "price") BigDecimal price,
            @RequestParam(value = "stockQuantity") Integer stockQuantity,
            @RequestParam(value = "categoryId") Long categoryId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "productId") Long productId) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setSku(sku);
        productDTO.setPrice(price);
        productDTO.setStockQuantity(stockQuantity);
        productDTO.setCategoryId(categoryId);
        productDTO.setDescription(description);
        productDTO.setProductId(productId);

        Response response = productService.updateProduct(productDTO, imageFile);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/all")
    public ResponseEntity<Response> getProducts(){
        Response products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProductById( @PathVariable Long id){
        Response product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProduct( @PathVariable Long id){
        Response product = productService.deleteProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchProduct(@RequestParam("input") String input){
        Response product = productService.searchProduct(input);
        return ResponseEntity.ok(product);
    }

    // Opcion JSON + archivos
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Response> saveProduct(
//            @RequestPart("imageFile") MultipartFile imageFile,
//            @RequestPart("product") ProductRequest product
//    )
}
