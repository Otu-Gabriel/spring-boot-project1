package com.userApi.userApi.dto;

import jakarta.validation.constraints.NotBlank;

public record BrandRequestDTO (
        @NotBlank(message = "Brand name must not be blank")
        String name,

        String description
){
}
