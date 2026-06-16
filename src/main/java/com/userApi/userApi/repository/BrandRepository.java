package com.userApi.userApi.repository;

import com.userApi.userApi.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Page<Brand> findAll(Pageable pageable);

@Query(value = "SELECT u.* FROM brand u WHERE lower(u.name) like LOWER(CONCAT ('%', :name, '%'))",countQuery = "SELECT count(u.*) FROM brand u WHERE lower(u.name) like LOWER(CONCAT('%', :name, '%'))", nativeQuery = true)
    Page<Brand> findBrandByName(@Param("name") String name, Pageable pageable);

}
