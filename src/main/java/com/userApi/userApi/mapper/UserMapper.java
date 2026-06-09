package com.userApi.userApi.mapper;


import com.userApi.userApi.dto.UserRequestDTO;
import com.userApi.userApi.dto.UserResponseDTO;
import com.userApi.userApi.entity.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user) {
        if (user == null) return null;
//        return new UserResponseDTO(
//                user.getId(),
//                user.getName(),
//                user.getEmail(),
//                user.getSex(),
//                user.getPhone()
//        );

        UserResponseDTO userResponseDTO = UserResponseDTO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .sex(user.getSex())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();

        return userResponseDTO;
    }

    public static User toEntity(UserRequestDTO dto) {
        if (dto == null) return null;
//        return new User(
//                dto.name(),
//                dto.email(),
//                dto.sex(),
//                dto.phone(),
//                dto.password()
//        );
        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .sex(dto.sex())
                .phone(dto.phone())
                .role(dto.role())
                .build();

        return user;

}}