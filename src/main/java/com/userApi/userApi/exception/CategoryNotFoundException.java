package com.userApi.userApi.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Long id) {
        super("Category with ID " + id + "Not Found");
    }
}
