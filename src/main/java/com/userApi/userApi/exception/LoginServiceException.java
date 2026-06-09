package com.userApi.userApi.exception;

public class LoginServiceException extends RuntimeException {
    public LoginServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}