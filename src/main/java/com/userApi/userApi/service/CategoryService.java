package com.userApi.userApi.service;

import com.userApi.userApi.dto.CategoryRequestDTO;
import com.userApi.userApi.dto.CategoryResponseDTO;
import com.userApi.userApi.entity.Category;
import com.userApi.userApi.exception.CategoryNotFoundException;
import com.userApi.userApi.mapper.CategoryMapper;
import com.userApi.userApi.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO){
        log.info("Inside category service class");
        Category category = CategoryMapper.toCategoryEntity(categoryRequestDTO);

        Category savedCategory = categoryRepository.save(category);
        log.info("category saved successfully");
        return CategoryMapper.toCategoryDTO(savedCategory);
    }

    public List<CategoryResponseDTO>getAllCategory(){
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getByIdCategory(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        return CategoryMapper.toCategoryDTO(category);

    }

    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->new CategoryNotFoundException(id));

        if(categoryRequestDTO.name() != null){
            category.setName(categoryRequestDTO.name());

        }

        if (categoryRequestDTO.description() != null){
            category.setDescription(category.getDescription());
        }

        return CategoryMapper.toCategoryDTO(categoryRepository.save(category));
    }

    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}


