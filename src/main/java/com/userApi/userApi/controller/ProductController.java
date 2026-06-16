package com.userApi.userApi.controller;

import com.userApi.userApi.dto.ProductRequestDTO;
import com.userApi.userApi.dto.ProductResponseDTO;
import com.userApi.userApi.response.ApiResponse;
import com.userApi.userApi.response.PaginationResponse;
import com.userApi.userApi.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products")
@Tag(name = "Product API", description = "API for managing products")
public class ProductController {

    private final ProductService productService;

    // Injecting ProductService
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(@Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product created successfully", productService.createProduct(dto))
        );
    }

    // READ ALL (with pagination and optional filtering by name)
    @GetMapping
    public ResponseEntity<ApiResponse<PaginationResponse<ProductResponseDTO>>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Products retrieved", productService.getAll(page, size, name))
        );
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product found", productService.getProductById(id))
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product updated", productService.updateProduct(id, dto))
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Product deleted", null)
        );
    }
}
