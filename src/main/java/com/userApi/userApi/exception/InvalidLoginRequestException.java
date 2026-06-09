package com.userApi.userApi.exception;

public class InvalidLoginRequestException extends RuntimeException {
    public InvalidLoginRequestException(String message) {
        super(message);
    }
}