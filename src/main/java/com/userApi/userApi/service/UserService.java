package com.userApi.userApi.service;


import com.userApi.userApi.dto.UserRequestDTO;
import com.userApi.userApi.dto.UserResponseDTO;
import com.userApi.userApi.entity.User;
import com.userApi.userApi.exception.UserNotFoundException;
import com.userApi.userApi.mapper.UserMapper;
import com.userApi.userApi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponseDTO create(UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);
        User savedUser = repository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public List<UserResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return UserMapper.toDTO(user);
    }

    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if(dto.name() != null){
            user.setName(dto.name());
        }

        if (dto.email() != null){
            user.setEmail(dto.email());
        }

        if (dto.sex() != null){
            user.setSex(dto.sex());
        }

        if (dto.phone() != null){
            user.setPhone(dto.phone());
        }

        if (dto.password() != null){
            user.setPassword(dto.password());
        }

        return UserMapper.toDTO(repository.save(user));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}