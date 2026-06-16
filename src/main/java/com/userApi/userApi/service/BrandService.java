package com.userApi.userApi.service;

import com.userApi.userApi.dto.BrandRequestDTO;
import com.userApi.userApi.dto.BrandResponseDTO;
import com.userApi.userApi.entity.Brand;
import com.userApi.userApi.exception.BrandNotFoundException;
import com.userApi.userApi.mapper.BrandMapper;
import com.userApi.userApi.repository.BrandRepository;
import com.userApi.userApi.response.PaginationResponse;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BrandService {

    private final BrandRepository brandRepository;

//    injecting the brandRepository
    public BrandService(BrandRepository brandRepository){
        this.brandRepository = brandRepository;
    }

//    Create brand method
    public BrandResponseDTO createBrand(BrandRequestDTO brandRequestDTO){
        Brand brand = BrandMapper.toBrandEntity(brandRequestDTO);

        Brand savedBrand = brandRepository.save(brand);

        return BrandMapper.toBrandDTO(savedBrand);
    }

//    Get All Brand
    public PaginationResponse<BrandResponseDTO> getAll(int page, int size, String nameFilter){
        Pageable pageable = PageRequest.of(page,size, Sort.by("id").ascending());

        Page<Brand> brands;
        if (nameFilter != null && !nameFilter.isEmpty()) {
            brands = (Page<Brand>) brandRepository.findBrandByName(nameFilter, pageable);
        }else{
            brands = (Page<Brand>) brandRepository.findAll(pageable);
        }

        Page<BrandResponseDTO> dtoPage = brands.map(BrandMapper::toBrandDTO);

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

//    Find by ID
    public BrandResponseDTO getById(Long id){
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException(id));
        return BrandMapper.toBrandDTO(brand);

    }

//    Update brand

    public BrandResponseDTO update(Long id, BrandRequestDTO brandRequestDTO){
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException(id));

        if (brandRequestDTO.name() !=null){
            brand.setName(brandRequestDTO.name());
        }
        if(brandRequestDTO.description() != null){
            brand.setDescription(brandRequestDTO.description());
        }

        return BrandMapper.toBrandDTO(brandRepository.save(brand));
    }

//    Delete Brand
    public void delete(Long id){brandRepository.deleteById(id);}
}
