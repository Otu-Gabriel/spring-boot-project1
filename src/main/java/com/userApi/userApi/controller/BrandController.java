package com.userApi.userApi.controller;

import com.userApi.userApi.dto.BrandRequestDTO;
import com.userApi.userApi.dto.BrandResponseDTO;
import com.userApi.userApi.response.ApiResponse;
import com.userApi.userApi.response.PaginationResponse;
import com.userApi.userApi.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/brands")
@Tag(name = "Brand API", description = "Api for managing brands")
public class BrandController {
    private final BrandService brandService;

//    Injecting the brand service class
    public BrandController(BrandService brandService){
        this.brandService = brandService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponseDTO>> create(@Valid @RequestBody BrandRequestDTO dto){
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Brand created successfully", brandService.createBrand(dto))
        );
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<PaginationResponse<BrandResponseDTO>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name){
        return ResponseEntity.ok(
                new ApiResponse<>(true,"Brands Retrieved", brandService.getAll(page,size,name))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Brand found", brandService.getById(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> update(
            @PathVariable Long id,
            @RequestBody BrandRequestDTO dto) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Brand updated", brandService.update(id, dto))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Brand deleted", null)
        );
    }
}

