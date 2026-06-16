package com.userApi.userApi.exception;

public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(Long id){
        super("Brand with ID " + id + "not found");
    }
}
