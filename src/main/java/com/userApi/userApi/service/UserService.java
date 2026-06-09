package com.userApi.userApi.service;


import com.userApi.userApi.dto.UserRequestDTO;
import com.userApi.userApi.dto.UserResponseDTO;
import com.userApi.userApi.entity.User;
import com.userApi.userApi.exception.EmailAlreadyExistsException;
import com.userApi.userApi.exception.GlobalExceptionHandler;
import com.userApi.userApi.exception.UserNotFoundException;
import com.userApi.userApi.mapper.UserMapper;
import com.userApi.userApi.repository.UserRepository;
import com.userApi.userApi.response.PaginationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponseDTO create(UserRequestDTO dto) {
        if(repository.existsByEmail(dto.email())){
            throw new EmailAlreadyExistsException(dto.email());
        }
        User user = UserMapper.toEntity(dto);

        User savedUser = repository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public PaginationResponse<UserResponseDTO> getAll(int page, int size, String nameFilter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        Page<User> users;
        if (nameFilter != null && !nameFilter.isEmpty()) {
            users = (Page<User>) repository.findUsersByName(nameFilter, pageable);
        } else {
            users = (Page<User>) repository.findAll(pageable);
        }

        Page<UserResponseDTO> dtoPage = users.map(UserMapper::toDTO);

       return new PaginationResponse<>(
               dtoPage.getContent(),
               dtoPage.getNumber(),
               dtoPage.getSize(),
               dtoPage.getTotalElements(),
               dtoPage.getTotalPages(),
               dtoPage.hasNext(),
               dtoPage.hasPrevious()
       );

    }
//
//    public Page<UserResponseDTO> getAll(int page, int size, String nameFilter) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
//
//        Page<User> users;
//        if (nameFilter != null && !nameFilter.isEmpty()) {
//            users = (Page<User>) repository.findUsersByName(nameFilter, pageable);
//        } else {
//            users = (Page<User>) repository.findAll(pageable);
//        }
//
//        return users.map(UserMapper::toDTO);
//    }

//



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


        return UserMapper.toDTO(repository.save(user));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }


//    Create local user
public UserResponseDTO createLocalUser(
        UserRequestDTO dto,
        String keycloakId
) {

    if(repository.existsByEmail(dto.email())) {

        throw new EmailAlreadyExistsException(
                dto.email()
        );
    }

    User user = new User();

    user.setName(dto.name());
    user.setEmail(dto.email());
    user.setPhone(dto.phone());
    user.setSex(dto.sex());
    user.setRole(dto.role());
    user.setKeycloakId(keycloakId);

    User savedUser =
            repository.save(user);

    return UserMapper.toDTO(savedUser);
}

}