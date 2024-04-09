package it.academy.services;

import it.academy.DTO.request.CategoryDTO;
import it.academy.DTO.response.CategoriesDTO;
import lombok.NonNull;

public interface CategoryService {
    void addCategory(@NonNull CategoryDTO categoryDTO);
    CategoryDTO getCategoryById(@NonNull Long id);
    CategoryDTO getCategoryByName(@NonNull String name);
    CategoriesDTO getAllCategories();
}
