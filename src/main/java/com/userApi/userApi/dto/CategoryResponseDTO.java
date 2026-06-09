package com.userApi.userApi.dto;

import lombok.Builder;

@Builder
public record CategoryResponseDTO (Long id, String name, String description){
}
