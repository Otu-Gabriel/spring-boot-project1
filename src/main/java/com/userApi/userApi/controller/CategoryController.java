package com.userApi.userApi.controller;

import com.userApi.userApi.dto.CategoryRequestDTO;
import com.userApi.userApi.dto.CategoryResponseDTO;
import com.userApi.userApi.response.ApiResponse;
import com.userApi.userApi.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
        log.info("Category controller was reached");
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Category created successfully", categoryService.createCategory(categoryRequestDTO) )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>>getCategory(){
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Category retrieved", categoryService.getAllCategory())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getByIDCategory(@PathVariable Long id){
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Category Found", categoryService.getByIdCategory(id))
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequestDTO){
        return ResponseEntity.ok(
                new ApiResponse<>(true,"Category Updated", categoryService.updateCategory(id, categoryRequestDTO))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true,"Category deleted", null)
        );
    }


}
