package com.gonzalodev.InventoryMgtSystem.services.impl;

import com.gonzalodev.InventoryMgtSystem.dtos.CategoryDTO;
import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.exceptions.NotFoundException;
import com.gonzalodev.InventoryMgtSystem.models.Category;
import com.gonzalodev.InventoryMgtSystem.repositories.CategoryRepository;
import com.gonzalodev.InventoryMgtSystem.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public Response createCategory(CategoryDTO categoryDTO) {
        Category categoryToSave = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(categoryToSave);
        return Response.builder()
                .status(204)
                .message("Category created successfully")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        categories.forEach(category -> category.setProducts(null));
        List<CategoryDTO> categoryDTOList = modelMapper.map(categories, new TypeToken<List<CategoryDTO>>() {
        }.getType());
        return Response.builder()
                .status(200)
                .categories(categoryDTOList)
                .message("success")
                .build();
    }

    @Override
    public Response getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category Not Found"));
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return Response.builder()
                .status(200)
                .category(categoryDTO)
                .message("success")
                .build();
    }

    @Override
    public Response updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category Not Found"));
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return Response.builder()
                .status(200)
                .message("Category successfully updated")
                .build();
    }

    @Override
    public Response deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category Not Found"));
        categoryRepository.deleteById(category.getId());
        return Response.builder()
                .status(200)
                .message("Category successfully deleted")
                .build();
    }
}
