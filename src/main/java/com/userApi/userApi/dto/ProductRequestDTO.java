package com.userApi.userApi.dto;

import com.userApi.userApi.entity.Brand;
import com.userApi.userApi.entity.Category;
import jakarta.validation.constraints.NotBlank;

public record ProductRequestDTO(
        @NotBlank(message = "product name must not be empty")
        String name,

        double price,
        String description,

        Brand brand,

        Category category

) {
}
