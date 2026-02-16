package com.gonzalodev.InventoryMgtSystem.services;

import com.gonzalodev.InventoryMgtSystem.dtos.CategoryDTO;
import com.gonzalodev.InventoryMgtSystem.dtos.Response;

public interface CategoryService {

    Response createCategory(CategoryDTO categoryDTO);

    Response getAllCategories();

    Response getCategoryById(Long id);

    Response updateCategory(Long id, CategoryDTO categoryDTO);

    Response deleteCategory(Long id);


}
