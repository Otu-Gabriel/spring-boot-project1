package com.userApi.userApi.mapper;

import com.userApi.userApi.dto.CategoryRequestDTO;
import com.userApi.userApi.dto.CategoryResponseDTO;
import com.userApi.userApi.entity.Category;

public class CategoryMapper {
    public static CategoryResponseDTO toCategoryDTO(Category category){
        if (category ==null) return null;

        CategoryResponseDTO categoryResponseDTO = CategoryResponseDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();

        return categoryResponseDTO;

    }

    public static Category toCategoryEntity(CategoryRequestDTO categoryDTO){
        if (categoryDTO == null) return null;

        Category category = Category.builder()
                .name(categoryDTO.name())
                .description(categoryDTO.description())
                .build();

        return category;
    }
}
