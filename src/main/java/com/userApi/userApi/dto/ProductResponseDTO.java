package com.userApi.userApi.dto;

import com.userApi.userApi.entity.Brand;
import com.userApi.userApi.entity.Category;
import lombok.Builder;

@Builder
public record ProductResponseDTO(
        Long id,
        String name,
        double price,
        String description,
        Brand brand,
        Category category
) {
}
