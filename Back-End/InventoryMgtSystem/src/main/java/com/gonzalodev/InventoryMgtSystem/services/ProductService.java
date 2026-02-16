package com.gonzalodev.InventoryMgtSystem.services;

import com.gonzalodev.InventoryMgtSystem.dtos.ProductDTO;
import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    Response saveProduct(ProductDTO productDTO, MultipartFile imageFile);

    Response getAllProducts();

    Response getProductById(Long id);

    Response updateProduct(ProductDTO productDTO, MultipartFile imageFile);

    Response deleteProduct(Long id);

    Response searchProduct(String input);
}
