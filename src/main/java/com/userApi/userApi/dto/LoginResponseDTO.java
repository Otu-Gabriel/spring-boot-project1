package com.userApi.userApi.dto;

import lombok.Builder;

@Builder
public record LoginResponseDTO(

        String accessToken,
        String refreshToken,
        Long expiresIn,
        Long refreshExpiresIn,
        String tokenType

) {
}