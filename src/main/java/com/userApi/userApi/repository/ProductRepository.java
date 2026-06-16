package com.userApi.userApi.repository;

import com.userApi.userApi.entity.Brand;
import com.userApi.userApi.entity.Category;
import com.userApi.userApi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pegeable);
    Page<Product> findByCategory (Category category, Pageable pageable);
    Page<Product> findByBrand (Brand brand, Pageable pageable);

    @Query(value = "SELECT u.* FROM brand u WHERE lower(u.name) like LOWER(CONCAT ('%', :name, '%'))",countQuery = "SELECT count(u.*) FROM product u WHERE lower(u.name) like LOWER(CONCAT('%', :name, '%'))", nativeQuery = true)
    Page<Product> findProductByName(@Param("name") String name, Pageable pageable);

}
