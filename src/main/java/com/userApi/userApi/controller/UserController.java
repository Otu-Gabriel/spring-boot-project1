package com.userApi.userApi.controller;


import com.userApi.userApi.dto.UserRequestDTO;
import com.userApi.userApi.dto.UserResponseDTO;
import com.userApi.userApi.response.ApiResponse;
import com.userApi.userApi.response.PaginationResponse;
import com.userApi.userApi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "API for managing users")
@Slf4j
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> create(@Valid @RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User created", service.create(dto))
        );
    }

//    @GetMapping()
//    public Page<UserResponseDTO> getAllUsers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String name) {
//        return service.getAll(page, size, name);
//    }


    @GetMapping()
    public ResponseEntity<ApiResponse<PaginationResponse<UserResponseDTO>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name){
        return ResponseEntity.ok(
                new ApiResponse<>(true,"Users Retrieved", service.getAll(page,size,name))
        );
    }

//    @GetMapping("/list")
//    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getUsersInList(
//            @RequestParam (value = "name") String name)
//    {
//        log.info("Call came here with RequestParam {}", name);
//        return ResponseEntity.ok(
//                new ApiResponse<>(true,"Users List Retrieved", service.findUsersByNames(name))
//        );
//    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User found", service.getById(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> update(
            @PathVariable Long id,
            @RequestBody UserRequestDTO dto) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User updated", service.update(id, dto))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "User deleted", null)
        );
    }
}