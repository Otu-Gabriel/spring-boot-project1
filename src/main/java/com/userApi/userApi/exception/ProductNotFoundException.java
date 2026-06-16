package com.userApi.userApi.exception;

public class ProductNotFoundException extends RuntimeException  {
    public ProductNotFoundException(Long id){
        super("Brand with ID " + id + "not found");
    }
}
