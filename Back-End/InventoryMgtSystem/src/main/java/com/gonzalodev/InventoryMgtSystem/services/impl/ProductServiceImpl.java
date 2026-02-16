package com.gonzalodev.InventoryMgtSystem.services.impl;

import com.gonzalodev.InventoryMgtSystem.dtos.ProductDTO;
import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.exceptions.NotFoundException;
import com.gonzalodev.InventoryMgtSystem.models.Category;
import com.gonzalodev.InventoryMgtSystem.models.Product;
import com.gonzalodev.InventoryMgtSystem.repositories.CategoryRepository;
import com.gonzalodev.InventoryMgtSystem.repositories.ProductRepository;
import com.gonzalodev.InventoryMgtSystem.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    // get access to de user's working directory  (app's root directory)
    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir")+"/product-images/";

    @Override
    public Response saveProduct(ProductDTO productDTO, MultipartFile imageFile) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()-> new NotFoundException("Category Not Found"));
        Product productToSave = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .category(category)
                .price(productDTO.getPrice())
                .sku(productDTO.getSku())
                .stockQuantity(productDTO.getStockQuantity())
                .build();

        if(imageFile != null && !imageFile.isEmpty()){
            log.info("Image file exists");

            String imagePath = saveImage(imageFile);
            System.out.println("IMAGE URL IS: " + imagePath);
            productToSave.setImgUrl(imagePath);
        }
        productRepository.save(productToSave);

        return Response.builder()
                .status(204)
                .message("Product created successfully")
                .build();
    }

    @Override
    public Response updateProduct(ProductDTO productDTO, MultipartFile imageFile) {
        Product existingProduct = productRepository.findById(productDTO.getProductId())
                .orElseThrow(()-> new NotFoundException("Product not found"));
        if(imageFile != null && !imageFile.isEmpty()){
            String imagePath = saveImage(imageFile);
            existingProduct.setImgUrl(imagePath);
        }

        // check category
        if(productDTO.getCategoryId()!=null && productDTO.getCategoryId()>0){
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(()-> new NotFoundException("Category not found"));
            existingProduct.setCategory(category);
        }
        // check product name
        if(productDTO.getName()!=null && !productDTO.getName().isBlank()){
            existingProduct.setName(productDTO.getName());
        }
        // check SKU
        if(productDTO.getSku()!=null && !productDTO.getSku().isBlank()){
            existingProduct.setSku(productDTO.getSku());
        }
        // check description
        if(productDTO.getDescription()!=null && !productDTO.getDescription().isBlank()){
            existingProduct.setDescription(productDTO.getDescription());
        }
        // check product
        if(productDTO.getPrice()!=null && productDTO.getPrice().compareTo(BigDecimal.ZERO) >= 0){
            existingProduct.setPrice(productDTO.getPrice());
        }
        // check Stock
        if(productDTO.getStockQuantity()!=null && productDTO.getStockQuantity()>0){
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
        }
        productRepository.save(existingProduct);

        return Response.builder()
                .status(200)
                .message("Product updated successfully")
                .build();
    }


    @Override
    public Response getAllProducts() {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<ProductDTO> productDTOList = modelMapper.map(products, new TypeToken<List<ProductDTO>>() {
        }.getType());
        return Response.builder()
                .products(productDTOList)
                .status(200)
                .message("success")
                .build();
    }

    @Override
    public Response getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        return Response.builder()
                .status(200)
                .message("success")
                .product(productDTO)
                .build();
    }

    @Override
    public Response deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.deleteById(product.getId());
        return Response.builder()
                .status(200)
                .message("Product deleted successfully")
                .build();
    }

    @Override
    public Response searchProduct(String input) {
        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(input, input);
        List<ProductDTO> productDTOList = modelMapper.map(products, new TypeToken<List<ProductDTO>>() {
        }.getType());
        return Response.builder()
                .products(productDTOList)
                .status(200)
                .message("success")
                .build();
    }

    public String saveImage(MultipartFile imageFile){
        // validate that the images is not greater than 5 mb
        if(!Objects.requireNonNull(imageFile.getContentType()).startsWith("image/") || imageFile.getSize() > 1024 * 1024 * 5){
            throw new IllegalArgumentException("Only image files under 5mb are allowed");
        }
        // create the directory id it doesn't exist
        File directory = new File(IMAGE_DIRECTORY);
        if(!directory.exists()){
             if(!directory.mkdir()){
                throw new IllegalArgumentException("Unable to create the directory");
            }
            log.info("Directory was created");
        }
        // create a unique file name
        String uniqueFileName = UUID.randomUUID() +"_"+imageFile.getOriginalFilename();
        log.info("The file names is: {}", uniqueFileName);

        String imagePath = IMAGE_DIRECTORY+uniqueFileName;

       try{
           File destinationFile = new File(imagePath);
           // writing the image to the specific location
           imageFile.transferTo(destinationFile);
       } catch (Exception e) {
           throw new IllegalArgumentException("Error saving Image: " + e.getMessage());
       }
       return imagePath;
    }
}
