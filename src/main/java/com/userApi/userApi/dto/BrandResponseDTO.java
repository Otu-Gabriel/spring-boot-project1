package com.userApi.userApi.dto;

import lombok.Builder;

@Builder
public record BrandResponseDTO(
        Long id,
        String name,
        String description
) {
}
