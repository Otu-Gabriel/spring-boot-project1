package com.userApi.userApi.mapper;

import com.userApi.userApi.dto.BrandRequestDTO;
import com.userApi.userApi.dto.BrandResponseDTO;
import com.userApi.userApi.dto.BrandResponseDTO;
import com.userApi.userApi.entity.Brand;

public class BrandMapper {
    public static BrandResponseDTO toBrandDTO(Brand brand){
        if (brand == null) return null;
        BrandResponseDTO brandResponseDTO= BrandResponseDTO
                .builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .build();

        return brandResponseDTO;
    }

    public static Brand toBrandEntity(BrandRequestDTO brandRequestDTO){
        if (brandRequestDTO == null) return null;

        Brand brand = Brand
                .builder()
                .name(brandRequestDTO.name())
                .description(brandRequestDTO.description())
                .build();
        return brand;
    }
}
