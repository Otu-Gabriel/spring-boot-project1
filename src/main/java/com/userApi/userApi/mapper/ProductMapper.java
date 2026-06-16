package com.userApi.userApi.mapper;

import com.userApi.userApi.dto.ProductRequestDTO;
import com.userApi.userApi.dto.ProductResponseDTO;
import com.userApi.userApi.entity.Product;

public class ProductMapper {
    public static ProductResponseDTO toProductDTO(Product product){
        if (product == null) return null;
        ProductResponseDTO productResponseDTO = ProductResponseDTO
                .builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .brand(product.getBrand())
                .category(product.getCategory())
                .build();

        return productResponseDTO;
    }

    public static Product toProductEntity(ProductRequestDTO productRequestDTO){
        if (productRequestDTO == null) return null;

        Product product = Product
                .builder()
                .name(productRequestDTO.name())
                .price(productRequestDTO.price())
                .description(productRequestDTO.description())
                .brand(productRequestDTO.brand())
                .category(productRequestDTO.category())
                .build();
        return product;
    }
}
