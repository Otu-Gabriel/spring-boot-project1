package com.userApi.userApi.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
        @NotBlank(message = "Category name must not be empty")
        String name,

        @NotBlank(message = "Category description must not be empty")
        String description
) {}
