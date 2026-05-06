package com.userApi.userApi.dto;

import lombok.Builder;

@Builder
public record UserResponseDTO(
        long id,
        String name,
        String email,
        String sex,
        String phone
) {}
