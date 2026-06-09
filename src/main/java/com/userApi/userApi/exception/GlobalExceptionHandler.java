package com.userApi.userApi.exception;


import com.userApi.userApi.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle specific exception

    //User not found Exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>(false, ex.getMessage(), null)
        );
    }

    // Category not found exception
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCategoryNotFound( CategoryNotFoundException ex){
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ApiResponse<>(false, ex.getMessage(),null)
       );

    }

    //
//    Keycloak Role not found exception
    @ExceptionHandler(KeycloakUserCreationException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserCreationError(KeycloakUserCreationException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiResponse<>(false, ex.getMessage(), null)
        );
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ApiResponse<Void>>handleInvalidRoleException(InvalidRoleException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>(false, ex.getMessage(), null)
        );
    }

    //    Fields Valiation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>(false, "Validation failed", errors)
        );
    }


    //    Email already exist exception
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExists(EmailAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiResponse<>(false, ex.getMessage(), null)
        );
    }


    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>(false, "An unexpected error occurred", null)
        );
    }
}