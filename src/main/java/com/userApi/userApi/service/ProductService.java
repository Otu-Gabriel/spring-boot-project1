package com.userApi.userApi.service;

import com.userApi.userApi.dto.BrandResponseDTO;
import com.userApi.userApi.dto.ProductRequestDTO;
import com.userApi.userApi.dto.ProductResponseDTO;
import com.userApi.userApi.entity.Brand;
import com.userApi.userApi.entity.Category;
import com.userApi.userApi.entity.Product;
import com.userApi.userApi.exception.BrandNotFoundException;
import com.userApi.userApi.exception.CategoryNotFoundException;
import com.userApi.userApi.exception.ProductNotFoundException;
import com.userApi.userApi.mapper.BrandMapper;
import com.userApi.userApi.mapper.ProductMapper;
import com.userApi.userApi.repository.BrandRepository;
import com.userApi.userApi.repository.CategoryRepository;
import com.userApi.userApi.repository.ProductRepository;
import com.userApi.userApi.response.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          BrandRepository brandRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    // CREATE
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Brand brand = brandRepository.findById(dto.brand().getId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Category category = categoryRepository.findById(dto.category().getId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.category().getId()));

        Product product = Product.builder()
                .name(dto.name())
                .price(dto.price())
                .description(dto.description())
                .brand(brand)
                .category(category)
                .build();

        Product saved = productRepository.save(product);
        return ProductMapper.toProductDTO(saved);
    }

    // READ (single product)
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toProductDTO(product);
    }

    // READ (all products)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toProductDTO)
                .toList();
    }

    //    Get All Product
    public PaginationResponse<ProductResponseDTO> getAll(int page, int size, String nameFilter){
        Pageable pageable = PageRequest.of(page,size, Sort.by("id").ascending());

        Page<Product> products;
        if (nameFilter != null && !nameFilter.isEmpty()) {
            products = (Page<Product>) productRepository.findProductByName(nameFilter, pageable);
        }else{
            products = (Page<Product>) productRepository.findAll(pageable);
        }

        Page<ProductResponseDTO> dtoPage = products.map(ProductMapper::toProductDTO);

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


    // UPDATE
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Brand brand = brandRepository.findById(dto.brand().getId())
                .orElseThrow(() -> new BrandNotFoundException(dto.brand().getId()));
        Category category = categoryRepository.findById(dto.category().getId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.category().getId()));

        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        product.setBrand(brand);
        product.setCategory(category);

        Product updated = productRepository.save(product);
        return ProductMapper.toProductDTO(updated);
    }

    // DELETE
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}
