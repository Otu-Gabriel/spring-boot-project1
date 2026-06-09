package com.userApi.userApi.repository;


import com.userApi.userApi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

//    @Query(value = "SELECT u.* FROM users u WHERE u.name like %:name%", nativeQuery = true)
//    List<User> findUsersByName(@Param("name") String name);

 @Query(value = "SELECT u.* FROM users u WHERE lower( u.name ) like LOWER(CONCAT('%', :name, '%'))",countQuery ="SELECT count(u.*) FROM users u WHERE lower( u.name) like LOWER(CONCAT('%', :name, '%'))" , nativeQuery = true)
    Page<User> findUsersByName(@Param("name") String name, Pageable pageable);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}